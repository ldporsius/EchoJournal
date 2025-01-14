package nl.codingwithlinda.echojournal.feature_record.data

import android.app.Application
import android.media.MediaRecorder
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.core.domain.util.ECHO_JOURNAL_DIR
import nl.codingwithlinda.echojournal.feature_record.data.finite_state.RecorderStateStopped
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState
import java.io.File
import java.io.FileWriter
import java.io.IOException

class AndroidMediaRecorder(
    private val context: Application,
    private val dispatcherProvider: DispatcherProvider,
): AudioRecorder{

    private var _recorderState: RecorderState = RecorderStateStopped(this)
    private val _recorderStateFlow = MutableStateFlow(_recorderState)
    override val recorderState: Flow<RecorderState>
        get() = _recorderStateFlow

    private val FILE_NAME_AUDIO: String = "echoJournal.mp4"
    private val FILE_NAME_AMPLITUDES = "audio_waves.txt"

    private val pathAmplitudes: String = File(context.filesDir, FILE_NAME_AMPLITUDES).path
    private var pathAudio: File = File(context.filesDir, ECHO_JOURNAL_DIR )

    private var startRecordingTime: Long = 0L
    private var endRecordingTime: Long = 0L
    private val _waves = MutableStateFlow<List<Int>>(emptyList())
    private val samplingRate = 8_000

    override val listener: Flow<AudioRecorderData> = flow {
        emit(AudioRecorderData(
            duration = endRecordingTime - startRecordingTime,
            uri = FILE_NAME_AUDIO,
            amplitudesUri = pathAmplitudes
        )
        )
    }

    override fun changeState(state: RecorderState): RecorderState {
        _recorderState = state
        _recorderStateFlow.update { state }
        return state
    }

    ///////// user interaction ///////////////////////////////////
    override fun onCancelAction() {
        _recorderState.cancel()
    }

    override fun onMainAction() {
        _recorderState.main()
    }

    override fun onSecondaryAction() {
        _recorderState.secondary()
    }
    ///////////////////////////////////////////////////////////////

    private var recorder: MediaRecorder? = null

    private var isRecording: Boolean = false

    private fun startRecording() {
        println("AndroidMediaRecorder started recording on path: $pathAudio")
        if(!pathAudio.exists()){
            pathAudio.mkdirs()
        }
        val internalStoragePath = File(pathAudio, FILE_NAME_AUDIO).path

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(internalStoragePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setAudioSamplingRate(samplingRate)

            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            start()
            startRecordingTime = System.currentTimeMillis();
        }

        println("started recording")

        isRecording = true

        CoroutineScope(dispatcherProvider.default).launch {
            recordAmplitudes()
        }
    }

    private suspend fun recordAmplitudes(){
        val recordAmplitudeIntervalMillis = 30L
        while (isRecording) {
            recorder?.maxAmplitude?.let {
                _waves.value += it
            }
            delay(recordAmplitudeIntervalMillis)
        }
    }

    private fun writeAmplitudesToFile(){
        val output = _waves.value.toList()

        val fileWriter = FileWriter(pathAmplitudes)

        CoroutineScope(dispatcherProvider.io).launch{
            fileWriter.use {
                it.write(output.joinToString(","))
            }
        }
        _waves.value = emptyList()
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
        }

        recorder = null
    }

    private fun stopRecording() {
        println("stopped recording")

        isRecording = false
        endRecordingTime = System.currentTimeMillis();

        try {
            recorder?.run {
                stop()
                release()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        recorder = null

        writeAmplitudesToFile()
    }

    private fun pauseRecording(){
        println("paused recording")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            recorder?.pause()
        }
        else {
            recorder?.stop()
        }
    }

    private fun resumeRecording(){
        println("resume recording")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            recorder?.resume()
        else
            recorder?.start()
    }

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