package nl.codingwithlinda.echojournal.feature_create.presentation.state

data class CreateEchoUiState(
    val title: String = "",
    val topic: String = "",
    val topics: List<String> = listOf(),
    val isTopicsExpanded: Boolean = false
)
