package nl.codingwithlinda.echojournal.core.domain

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

interface EchoPlayer {
    suspend fun amplitudes(uri: Uri): List<Float>
    fun play(uri: Uri)
    fun pause()
    fun resume()
    fun stop()
    val waves: StateFlow<ByteArray>

}