package nl.codingwithlinda.echojournal.core.data

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
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
import nl.codingwithlinda.echojournal.core.domain.util.ECHO_JOURNAL_DIR
import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState
import java.io.File
import java.io.FileReader
import kotlin.math.ceil
import kotlin.math.roundToInt


class AndroidEchoPlayer(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
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

    private fun amplitudesFromFile(uri: Uri): List<Float> {
        val BAR_NUM = 50

        val fileReader = FileReader(uri.path)
        fileReader.use { reader ->
            val amplitudes = reader.readLines().flatMap { lines ->
                lines.split(",").map {
                    it.toFloat()
                }
            }

            val chunkSize = ceil(amplitudes.size.toFloat() / BAR_NUM).roundToInt().coerceAtLeast(1)

            val maxAmplitude = amplitudes.maxOrNull() ?: 1f

            return amplitudes.chunked(chunkSize) {
                it.max() / maxAmplitude
            }.map {
                it.coerceAtLeast(0.075f)
            }
        }
    }

    private var playingTimeLeft = player?.duration ?: 0

    override fun play(id: String) {
        releaseMediaPlayer()

        val dir = File(context.filesDir, ECHO_JOURNAL_DIR)
        val uri = File(dir, id).toUri()
        player = MediaPlayer.create(context, uri)

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
