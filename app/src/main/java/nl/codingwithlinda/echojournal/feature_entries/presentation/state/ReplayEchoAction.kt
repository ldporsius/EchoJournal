package nl.codingwithlinda.echojournal.feature_entries.presentation.state

sealed interface ReplayEchoAction {
    data class Play(val id: String, val uri: String) : ReplayEchoAction
}