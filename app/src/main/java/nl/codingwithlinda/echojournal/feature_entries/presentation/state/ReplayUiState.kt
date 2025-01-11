package nl.codingwithlinda.echojournal.feature_entries.presentation.state

import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState

data class ReplayUiState(
    val playbackState: PlaybackState = PlaybackState.STOPPED,
    val playingEchoUri: String? = null,
    val waves: List<Float> = emptyList()
)
