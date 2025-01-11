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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer
import nl.codingwithlinda.echojournal.core.domain.SoundCapturer
import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState
import nl.codingwithlinda.echojournal.feature_record.data.AndroidMediaRecorder
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.OutputStream
import java.nio.ByteBuffer
import kotlin.math.ceil
import kotlin.math.roundToInt


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
    private var playbackState: PlaybackState = PlaybackState.STOPPED

    override suspend fun amplitudes(uri: Uri): List<Float> {

        val scheme = uri.scheme ?: ""
        val pathAmplitudesTest = File(context.filesDir, AndroidMediaRecorder.FILE_NAME_AMPLITUDES).path

        if (scheme.contains("resource")) {
            return withContext(dispatcherProvider.default) {
                amplitudesFromFile(Uri.parse(pathAmplitudesTest))
            }
        }

        return withContext(dispatcherProvider.default) {
                amplitudesFromFile(uri).also {
                    println("Amplitudes: ${it.size}")
                }
            }
        }

    private fun amplitudesFromBytes(uri: Uri): List<Float> {
        val inputStream =
            context.contentResolver.openInputStream(uri) ?: return emptyList()
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

            return res
        }
    }

    private fun amplitudesFromFile(uri: Uri): List<Float> {
        val BAR_NUM = 100
        val fileReader = FileReader(uri.path)
        fileReader.use { reader ->
            val amplitudes = reader.readLines().flatMap {
                it.split(",").map {
                    it.toFloat()
                }
            }
            println("Amplitudes input stream read bytes: ${amplitudes}")
            println("Amplitudes size: ${amplitudes.size}")
            println("Amplitudes min/max: ${amplitudes.minOrNull()}, ${amplitudes.maxOrNull()}")
            val chunkSize = ceil(1.0 * amplitudes.size / BAR_NUM).roundToInt().coerceAtLeast(1)
            println("Amplitudes chunk size: $chunkSize")

            val maxAmplitude = amplitudes.maxOrNull() ?: 1f
            return amplitudes.chunked(chunkSize) {
                it.max().toFloat() / maxAmplitude
            }
        }
    }
    private var playingTimeLeft = player?.duration ?: 0

    override fun play(uri: Uri) {
        releaseMediaPlayer()

        player = MediaPlayer.create(context, uri)

        player?.start()

        CoroutineScope(dispatcherProvider.default).launch {
            while (playingTimeLeft > 0) {
                if (playbackState == PlaybackState.PLAYING) {
                    playingTimeLeft -= 100
                    delay(100)
                }
            }
        }
    }

    override fun pause() {
        playbackState = PlaybackState.PAUSED
        player?.pause()
        soundCapturer.stop()
    }
    override fun resume() {
        playbackState = PlaybackState.PLAYING
        player?.run {
            seekTo(playingTimeLeft)
            start()
        }
    }

    override fun stop() {
        playbackState = PlaybackState.STOPPED
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