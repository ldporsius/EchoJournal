package nl.codingwithlinda.echojournal.feature_record.data

import android.app.Application
import android.media.MediaRecorder
import android.media.audiofx.Visualizer
import android.media.audiofx.Visualizer.MEASUREMENT_MODE_PEAK_RMS
import android.os.Build
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class AndroidMediaRecorder(
    private val context: Application,
    private val dispatcherProvider: DispatcherProvider
): AudioRecorder{
    private var fileName: String = File(context.filesDir,"audio.mp4").path

    private val _waves = MutableStateFlow<ByteArray>(byteArrayOf())
    private val samplingRate = 8_000

    override val listener: Flow<AudioRecorderData>
         = _waves.map {
        AudioRecorderData(
            duration = ((it.size / samplingRate.toFloat()) * 1000).toLong(),
            uri = fileName
        )

    }

    private var recorder: MediaRecorder? = null
    private val visualizer: Visualizer = Visualizer(0)

    private var isRecording: Boolean = false

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(fileName)
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

    }

    private fun stopRecording() {

        recorder?.let {
            println("maxAmplitude = ${it.maxAmplitude}")
        }
        recorder?.apply {
            stop()
            release()
        }
        recorder = null

        visualizer.setEnabled(false)

       /* val input = FileInputStream(fileName)

        try {
           val read = input.readBytes()
            println("bytes: ${read.joinToString(",")} ")
            println("read ${read.size} , bytes")
            _waves.update {
                read.clone()
            }

        }catch (e: Exception){
            e.printStackTrace()
        }*/
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

        isRecording = false
        stopRecording()

    }


}