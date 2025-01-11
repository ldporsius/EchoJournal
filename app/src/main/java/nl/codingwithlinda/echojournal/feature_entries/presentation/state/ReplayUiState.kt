package nl.codingwithlinda.echojournal.feature_entries.presentation.state

import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState

data class ReplayUiState(
    val playbackState: PlaybackState = PlaybackState.STOPPED,
    //val playingEchoUri: String? = null,
    val waves: List<Int> = emptyList()
){
    fun amplitudeColor(index: Int): androidx.compose.ui.graphics.Color {
        return if (index in waves) {
            androidx.compose.ui.graphics.Color.Blue
        } else {
            androidx.compose.ui.graphics.Color.DarkGray
        }
    }
}
