package nl.codingwithlinda.echojournal.feature_record.data

import android.app.Application
import android.media.MediaRecorder
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.core.domain.util.ECHO_JOURNAL_DIR
import nl.codingwithlinda.echojournal.core.domain.util.EchoResult
import nl.codingwithlinda.echojournal.feature_record.data.finite_state.RecorderStatePaused
import nl.codingwithlinda.echojournal.feature_record.data.finite_state.RecorderStateRecording
import nl.codingwithlinda.echojournal.feature_record.data.finite_state.RecorderStateStopped
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.domain.error.RecordingFailedError
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.finite.Counter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class AndroidMediaRecorder(
    private val context: Application,
    private val dispatcherProvider: DispatcherProvider,
): AudioRecorder{

    private var recorder: MediaRecorder? = null
    private var isRecording: Boolean = false

    //state
    val recordingState = RecorderStateRecording(this)
    val pausedState = RecorderStatePaused(this)
    val stoppedState = RecorderStateStopped(this)

    private var _recorderState: RecorderState = RecorderStateStopped(this)
    private val _recorderStateFlow = MutableStateFlow<RecordingState>(RecordingState.STOPPED)
    override val recorderState: StateFlow<RecordingState> = _recorderStateFlow.asStateFlow()

    override fun changeState(state: RecorderState): RecorderState {
        println("CHANGING STATE IN ANDROID MEDIARECORDER TO: ${state.recordingEnum}")
        _recorderState = state
        _recorderStateFlow.update { state.recordingEnum }
        return state
    }
    //end state//

    override val countDuration: MutableStateFlow<Long> = MutableStateFlow(0L)

    private val counter = Counter(
        recordingState = {
            _recorderState.recordingEnum
        },
        result = {res ->
            //println("counter result: $res")
            countDuration.update {
                res
            }
        }
    )

    private val FILE_NAME_AUDIO: String = "echoJournal.mp4"
    private val FILE_NAME_AMPLITUDES = "audio_waves.txt"

    private val pathAmplitudes: String = File(context.filesDir, FILE_NAME_AMPLITUDES).path
    private var pathAudio: File = File(context.filesDir, ECHO_JOURNAL_DIR )

    private val _waves = MutableStateFlow<List<Int>>(emptyList())
    private val samplingRate = 8_000

    override val listener: MutableSharedFlow<EchoResult<AudioRecorderData, RecordingFailedError>> = MutableSharedFlow(10)

    private fun emitOnSuccess(){
        val res = EchoResult.Success<AudioRecorderData,RecordingFailedError>(
            data = AudioRecorderData(
                duration = countDuration.value,
                uri = FILE_NAME_AUDIO,
                amplitudesUri = pathAmplitudes
            )
        )
        listener.tryEmit(res)
    }

    private fun emitOnError(){
        val res = EchoResult.Error<AudioRecorderData, RecordingFailedError>(
            error = RecordingFailedError
        )
        listener.tryEmit(res)
    }

    ///////// user interaction ///////////////////////////////////

    override fun handleAction(action: RecordAudioAction){
        println("ANDROID MEDIA RECORDER CALLED WITH ACTION: $action")
       when(action){
           RecordAudioAction.onCancelClicked -> {
               _recorderState.cancel()
           }
           RecordAudioAction.onMainClicked -> {
               _recorderState.main()
           }
           RecordAudioAction.onSecondaryClicked -> {
               _recorderState.secondary()
           }
       }
    }
    ///////////////////////////////////////////////////////////////


    private fun startRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && context.checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) != android.content.pm.PackageManager.PERMISSION_GRANTED){
            emitOnError()
            return
        }
        println("AndroidMediaRecorder started recording on path: $pathAudio")
        if(!pathAudio.exists()){
            pathAudio.mkdirs()
        }
        val internalStoragePath = File(pathAudio, FILE_NAME_AUDIO).path

        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        }else
            MediaRecorder()

        recorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(internalStoragePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setAudioSamplingRate(samplingRate)

            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
                emitOnError()
                return
            }
        }

        println("started recording")

        isRecording = true
        changeState(recordingState)

        println("ANDROID MEDIA RECORDER HAS STATE: ${_recorderState.recordingEnum}")

        CoroutineScope(dispatcherProvider.default).launch {
            launch {
                counter.resetCounter()
                counter.startCounting()
            }
            launch {
                recordAmplitudes(){
                    isRecording
                }
            }
        }

        println("end start recording")
    }


    private fun cancelRecording(){
        isRecording = false
        try {
            recorder?.run {
                stop()
                release()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            recorder = null
        }
    }

    private fun stopRecording() {
        println("stopped recording")
        isRecording = false

        try {
            recorder?.run {
                stop()
                release()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            recorder = null
        }

        CoroutineScope(dispatcherProvider.io).launch {
            counter.resetCounter()

            writeAmplitudesToFile()
            println("wrote amplitudes to file")
            emitOnSuccess()

        }
    }

    private fun pauseRecording(){
        isRecording = false

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                println("SDK >= 24")
                recorder?.pause()
                println("paused recording")
            }
            else {
                println("SDK < 24")
                stopRecording()
            }
        }catch (e: Exception){
            emitOnError()
        }

    }

    private fun resumeRecording(){
        println("resume recording")

        isRecording = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            recorder?.resume()
        else
            recorder?.start()

        CoroutineScope(dispatcherProvider.default).launch {
            launch {
                counter.startCounting()
            }
            launch {
                recordAmplitudes(){
                    isRecording
                }
            }
        }
    }

    ///////////////////amplitudes//////////////////////////////////////
    private suspend fun recordAmplitudes( loopWhile: () -> Boolean){
        val recordAmplitudeIntervalMillis = 30L
        while (loopWhile()) {
            recorder?.maxAmplitude?.let {
                _waves.value += it
            }
            delay(recordAmplitudeIntervalMillis)
        }
    }

    private suspend fun writeAmplitudesToFile(){
        withContext(dispatcherProvider.io) {
            val output = _waves.value.toList()

            try {
                val fileWriter = FileWriter(pathAmplitudes)

                fileWriter.use {
                    it.write(output.joinToString(","))
                }
            }catch (e: Exception){
                this.ensureActive()
                e.printStackTrace()
                emitOnError()
            }

            _waves.value = emptyList()
        }
    }

/////////////////////////////////////////////////////
    override fun start() {
        startRecording()
    }

    override fun pause() {
        pauseRecording()
    }

    override fun resume() {
        resumeRecording()
    }

    override fun stop() {
        stopRecording()
    }

    override fun cancel() {
        cancelRecording()
    }
}