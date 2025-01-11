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
import androidx.lifecycle.viewModelScope
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
    private val soundCapturer: SoundCapturer
): EchoPlayer{

    private val _amplitudesPlayed = MutableStateFlow(emptyList<Int>())
    override val waves = _amplitudesPlayed.asStateFlow()

    /** Handles audio focus when playing a sound file  */
    private val mAudioManager: AudioManager? = null

    private var player: MediaPlayer? = null
    private val _playbackState = MutableStateFlow(PlaybackState.STOPPED)
    override val playbackState = _playbackState.asStateFlow()


    override suspend fun amplitudes(path: String): List<Float> {

        val pathAmplitudes = File(context.filesDir, path).path
        val uri = Uri.parse(pathAmplitudes)

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
            val amplitudes = reader.readLines().flatMap { lines ->
                lines.split(",").map {
                    it.toFloat()
                }
            }

            val chunkSize = ceil(1.0 * amplitudes.size / BAR_NUM).roundToInt().coerceAtLeast(1)

            val maxAmplitude = amplitudes.maxOrNull() ?: 1f
            return amplitudes.chunked(chunkSize) {
                it.max() / maxAmplitude
            }
        }
    }

    private var playingTimeLeft = player?.duration ?: 0

    override fun play(uri: Uri) {
        releaseMediaPlayer()

        val soundTest = File(context.filesDir, AndroidMediaRecorder.FILE_NAME_AUDIO).path
        val uriTest = Uri.parse(soundTest)

        player = MediaPlayer.create(context, uriTest)

        playingTimeLeft = player?.duration ?: 0

        player?.start().also {
            _playbackState.update { PlaybackState.PLAYING }
        }

        CoroutineScope(dispatcherProvider.default).launch {
            while (playingTimeLeft > 0) {
                if (playbackState.value == PlaybackState.PLAYING) {
                    playingTimeLeft -= 100
                    delay(100)
                }
            }
            stop()
        }
    }

    override fun pause() {
        _playbackState.update { PlaybackState.PAUSED }
        player?.pause()
        soundCapturer.stop()
    }
    override fun resume() {
        _playbackState.update {  PlaybackState.PLAYING }
        player?.run {
            seekTo(playingTimeLeft)
            start()
        }
    }

    override fun stop() {
        _playbackState.update { PlaybackState.STOPPED }
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

    override suspend fun visualiseAmplitudes(amplitudes: List<Float>, duration: Long){

        _amplitudesPlayed.update {
            emptyList()
        }
        val _duration = player?.duration?.toLong() ?: 1000L

        val amplitudesSilent = amplitudes.takeWhile {
            it < 1.0f
        }.size

        println("AndroidEchoPlayer delaying while silent for $amplitudesSilent millis")
        delay(amplitudesSilent * 1L)

        val delayMillis = _duration / amplitudes.size

        println("AndroidEchoPlayer amplitudes size = ${amplitudes.size}")
        println("AndroidEchoPlayer duration = ${_duration}")
        println("AndroidEchoPlayer delaying visualise by $delayMillis millis")

        (1 .. amplitudes.size).onEachIndexed { index, fl ->
            if (playbackState.value == PlaybackState.PLAYING) {
                _amplitudesPlayed.value += index
            }
            delay(delayMillis)
        }

        _amplitudesPlayed.update {
            emptyList()
        }
        _playbackState.update {
            PlaybackState.STOPPED
        }
    }
}
