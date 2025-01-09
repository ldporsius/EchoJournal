package nl.codingwithlinda.echojournal.core.data

import android.content.Context
import android.media.AudioManager
import android.media.MediaCodec
import android.media.MediaDataSource
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import android.media.audiofx.Visualizer.MEASUREMENT_MODE_PEAK_RMS
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.OutputStream
import java.nio.ByteBuffer


class AndroidEchoPlayer(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val audioSampleExtractor: AudioExtractorAMR
): EchoPlayer, Visualizer.OnDataCaptureListener {


    private val _waves = MutableStateFlow<ByteArray>(byteArrayOf())
    override val waves = _waves.asStateFlow()

    /** Handles audio focus when playing a sound file  */
    private val mAudioManager: AudioManager? = null

    private var player: MediaPlayer? = null
    private var visualizer = Visualizer(0)
    private val chunkSize = 100
    val mediaExtractor = MediaExtractor()


    override suspend fun amplitudes(uri: Uri): List<Float> {

        val scheme = uri.scheme ?: ""
        val childPath = uri.path?.split("/")?.last()
        val path = File(context.filesDir, "$childPath")

        println("Path in amplitudes: ${path.path}")

        if (scheme.contains("resource")) {
            return withContext(dispatcherProvider.default) {
                val txtPath = File(context.filesDir, "${childPath}.txt")

                val inputStream =
                    context.contentResolver.openInputStream(uri) ?: return@withContext emptyList()
                inputStream.use { input ->
                    val bytes = input.readBytes()

                    println("Bytes size: ${bytes.size}")
                    println("Bytes min/max: ${bytes.minOrNull()}, ${bytes.maxOrNull()}")

                    val shorts = bytes.asSequence().map {
                        it + 128
                    }

                    val res = shorts
                        .chunked(2) {
                            (it.average().toFloat() / 256f)
                        }.toList()


                    println("Res min/max: ${res.minOrNull()}, ${res.maxOrNull()}")
                    println("Res size: ${res.size}")

                    emptyList()
                }
            }
        }

           /* return withContext(dispatcherProvider.default) {
                 val res = context.contentResolver?.openInputStream(uri).use { inputStream ->
                     val bytes = inputStream?.readBytes() ?: byteArrayOf()
                     println("Amplitudes input stream read bytes: ${bytes.toList()}")
                     bytes.toList().chunked(chunkSize) {
                         val max = it.maxOrNull()?.toFloat() ?: 1f
                         val average = it.average().toFloat()
                        max
                     } ?: emptyList()
                 }

                 println("Amplitudes res for raw: ${res}")
                 res
             }*/


        return audioSampleExtractor.extractSamples(audioPath = path.path).toList()

        return withContext(dispatcherProvider.default) {
            val inputStream = FileInputStream(uri.path)
            inputStream.use{
                it.readBytes().toList().chunked(1000){
                    1 / it.average().toFloat()
                }
            }
        }
    }


    override fun play(uri: Uri) {
        releaseMediaPlayer()

        player = MediaPlayer.create(context, uri)
        player?.start()

        visualiseRpm()
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
        visualizer.enabled = false
        visualizer.release()
        visualizer.setDataCaptureListener(null, 0, false, false);
    }

    private fun visualiseRpm(){
        player?.let {

            try {

                if (visualizer.enabled ) return
                visualizer.setDataCaptureListener(this, Visualizer.getMaxCaptureRate(), true, false)
                visualizer.captureSize = 256

                visualizer.setMeasurementMode(MEASUREMENT_MODE_PEAK_RMS)
                visualizer.setEnabled(true).also {
                    println("visualizer enabled: $it")
                }

            }catch (e: Exception){
                _waves.update {
                    ByteArray(0)
                }
                e.printStackTrace()
            }

        }
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private fun releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.

        if (player != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            player?.release()
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            player = null
            _waves.update {
                ByteArray(0)
            }
        }
    }

    override fun onWaveFormDataCapture(v: Visualizer?, waveform: ByteArray?, samplingRate: Int) {
        //println("Waveform: ${waveform?.joinToString(",")}")

        waveform?.let { b ->
            CoroutineScope(dispatcherProvider.default).launch {
                _waves.update {
                    b.clone()
                }
            }
        }
    }

    override fun onFftDataCapture(v: Visualizer?, bytes: ByteArray?, p2: Int) {
        println("fft: ${bytes?.joinToString(",")}")
    }
}