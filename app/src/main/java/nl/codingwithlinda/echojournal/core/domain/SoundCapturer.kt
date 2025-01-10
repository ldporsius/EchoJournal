package nl.codingwithlinda.echojournal.core.domain

import android.media.audiofx.Visualizer
import android.media.audiofx.Visualizer.MEASUREMENT_MODE_PEAK_RMS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SoundCapturer: Visualizer.OnDataCaptureListener {

    private val _waves = MutableStateFlow<ByteArray>(byteArrayOf())
    val waves = _waves.asStateFlow()

    private var visualizer: Visualizer? = null
    private val listener = this

    fun visualiseRpm(){

        try {

            visualizer = Visualizer(0)

            visualizer?.apply {
                setDataCaptureListener(listener, Visualizer.getMaxCaptureRate(), true, false)
                captureSize = 256
                setMeasurementMode(MEASUREMENT_MODE_PEAK_RMS).also {
                    println("visualizer measurement mode: $it")
                }
                setEnabled(true).also {
                    println("visualizer enabled: $it")
                }
            }

        }catch (e: Exception){
            _waves.update {
                ByteArray(0)
            }
            e.printStackTrace()
        }
    }

    fun stop(){
        try {
            visualizer?.apply {
                enabled = false
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            visualizer?.release()
            visualizer?.setDataCaptureListener(null, 0, false, false);
        }
    }

    override fun onWaveFormDataCapture(p0: Visualizer?, waveform: ByteArray?, p2: Int) {
        waveform?.let {
            println("SOUND CAPTURE waveform size = ${waveform.size}")
            println("SOUND CAPTURE waveform = ${waveform.contentToString()}")
            _waves.update {
               it.clone() + waveform.clone()
            }
        }
    }

    override fun onFftDataCapture(p0: Visualizer?, p1: ByteArray?, p2: Int) {

    }
}