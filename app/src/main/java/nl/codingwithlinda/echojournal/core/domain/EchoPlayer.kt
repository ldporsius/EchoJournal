package nl.codingwithlinda.echojournal.core.domain

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

interface EchoPlayer {
    fun play(uri: Uri)
    fun pause()
    fun stop()
    val waves: StateFlow<ByteArray>

}