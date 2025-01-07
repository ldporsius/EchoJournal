package nl.codingwithlinda.echojournal.feature_entries.presentation.state

data class ReplayUiState(
    val playingEchoId: String? = null,
    val waves: List<Float> = emptyList()
)
