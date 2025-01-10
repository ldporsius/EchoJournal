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
import nl.codingwithlinda.echojournal.core.domain.SoundCapturer
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.OutputStream
import java.nio.ByteBuffer


class AndroidEchoPlayer(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val audioSampleExtractor: AudioExtractorAMR,
    private val soundCapturer: SoundCapturer
): EchoPlayer{

    override val waves = soundCapturer.waves

    /** Handles audio focus when playing a sound file  */
    private val mAudioManager: AudioManager? = null

    private var player: MediaPlayer? = null

    override suspend fun amplitudes(uri: Uri): List<Float> {

        val scheme = uri.scheme ?: ""
        val childPath = uri.path?.split("/")?.last()
        val path = File(context.filesDir, "$childPath")
        val txtPath = File(context.filesDir, "${childPath}.txt")

        println("Path in amplitudes: ${path.path}")

        if (scheme.contains("resource")) {
            return withContext(dispatcherProvider.default) {

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

        return withContext(dispatcherProvider.default) {

            val fileReader = FileReader(uri.path)
            fileReader.use {
               val amplitudes =  it.readLines().flatMap {
                   it.split(",").map {
                       it.toFloat()
                   }
               }
                println("Amplitudes input stream read bytes: ${amplitudes}")
                println("Amplitudes size: ${amplitudes.size}")
                println("Amplitudes min/max: ${amplitudes.minOrNull()}, ${amplitudes.maxOrNull()}")
                val chunkSize = (amplitudes.size / 500) .coerceAtLeast(1)
                println("Amplitudes chunk size: $chunkSize")

                val maxAmplitude = amplitudes.maxOrNull() ?: 1f
                amplitudes.chunked(chunkSize){
                   it.average().toFloat() / maxAmplitude
                }
            }

        }
    }


    override fun play(uri: Uri) {
        releaseMediaPlayer()

        player = MediaPlayer.create(context, uri)
        player?.start()

        soundCapturer.visualiseRpm()
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
        soundCapturer.stop()
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

        }
    }

}