package nl.codingwithlinda.echojournal.core.domain

import android.net.Uri

interface EchoPlayer {
    fun play(uri: Uri)
    fun pause()
    fun stop()
}