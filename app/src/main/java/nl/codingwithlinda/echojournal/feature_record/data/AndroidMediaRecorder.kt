package nl.codingwithlinda.echojournal.feature_record.data

import android.app.Application
import android.media.MediaRecorder
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import java.io.File
import java.io.FileWriter
import java.io.IOException

class AndroidMediaRecorder(
    private val context: Application,
    private val dispatcherProvider: DispatcherProvider,

    ): AudioRecorder{
    private val pathAmplitudes: String = File(context.filesDir, FILE_NAME_AMPLITUDES).path
    private val pathAudio: String = File(context.filesDir,FILE_NAME_AUDIO).path

    companion object{
        const val FILE_NAME_AUDIO: String = "audio.mp4"
        const val FILE_NAME_AMPLITUDES = "audio_waves.txt"
    }

    private val _waves = MutableStateFlow<List<Int>>(emptyList())
    private val samplingRate = 8_000

    override val listener: Flow<AudioRecorderData>
            = _waves.map {
        AudioRecorderData(
            duration = (it.size * 100).toLong(),
            uri = pathAudio,
            amplitudesUri = pathAmplitudes
        )
    }

    private var recorder: MediaRecorder? = null

    private var isRecording: Boolean = false

    private fun startRecording() {
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
        }

        isRecording = true

        CoroutineScope(dispatcherProvider.default).launch {
            while (isRecording) {
                recorder?.maxAmplitude?.let {
                    _waves.value += it
                }
                delay(10)
            }
        }
    }

    private fun stopRecording() {

        isRecording = false
        recorder?.apply {
            stop()
            release()
        }
        recorder = null

        val output = _waves.value.toList()

        val fileWriter = FileWriter(pathAmplitudes)
        CoroutineScope(dispatcherProvider.io).launch{
            fileWriter.use {
                it.write(output.joinToString(","))
            }
        }
        _waves.value = emptyList()
    }

    override fun start() {
        println("started recording")
        startRecording()
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