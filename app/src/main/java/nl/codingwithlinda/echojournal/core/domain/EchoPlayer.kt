package nl.codingwithlinda.echojournal.core.domain

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow
import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState

interface EchoPlayer {
    suspend fun amplitudes(path: String): List<Float>
    suspend fun visualiseAmplitudes(amplitudes: List<Float>, duration: Long)
    fun play(uri: Uri)
    fun pause()
    fun resume()
    fun stop()
    val playbackState: StateFlow<PlaybackState>
    val waves: StateFlow<List<Int>>

}