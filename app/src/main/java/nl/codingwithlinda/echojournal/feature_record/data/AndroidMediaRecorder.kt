package nl.codingwithlinda.echojournal.feature_record.data

import android.app.Application
import android.media.MediaRecorder
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.core.domain.util.ECHO_JOURNAL_DIR
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import java.io.File
import java.io.FileWriter
import java.io.IOException

class AndroidMediaRecorder(
    private val context: Application,
    private val dispatcherProvider: DispatcherProvider,
    ): AudioRecorder{

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

    private var recorder: MediaRecorder? = null

    private var isRecording: Boolean = false

    private fun startRecording(pathAudio: String) {
        println("AndroidMediaRecorder started recording on path: $pathAudio")

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(pathAudio)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setAudioSamplingRate(samplingRate)

            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            start()
            startRecordingTime = System.currentTimeMillis();
        }

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

    private fun stopRecording() {
        isRecording = false
        endRecordingTime = System.currentTimeMillis();

        recorder?.apply {
            stop()
            release()
        }

        recorder = null

        writeAmplitudesToFile()
    }

    override fun start(path:String) {
        println("started recording")

        if(!pathAudio.exists()){
            pathAudio.mkdirs()
        }
        val internalStoragePath = File(pathAudio, FILE_NAME_AUDIO).path
        startRecording(internalStoragePath)
    }

    override fun pause() {
        println("paused recording")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            recorder?.pause()
        }
        stopRecording()
    }

    override fun stop() {
        println("stopped recording")
        stopRecording()
    }
}