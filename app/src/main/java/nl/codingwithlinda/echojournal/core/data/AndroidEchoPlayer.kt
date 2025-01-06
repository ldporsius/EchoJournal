package nl.codingwithlinda.echojournal.core.data

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import nl.codingwithlinda.echojournal.core.domain.EchoPlayer


class AndroidEchoPlayer(
    private val context: Context,
): EchoPlayer {

    /** Handles audio focus when playing a sound file  */
    private val mAudioManager: AudioManager? = null
    private var player: MediaPlayer? = null
    override fun play(uri: Uri) {
        releaseMediaPlayer()
        player = MediaPlayer.create(context, uri)
        player?.start()
        println("Playing ${uri.path ?: "no uri path"}")
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
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
}