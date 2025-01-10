package nl.codingwithlinda.echojournal.feature_entries.presentation.state

data class ReplayUiState(
    val playingEchoUri: String? = null,
    val waves: List<Float> = emptyList()
)
