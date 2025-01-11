package nl.codingwithlinda.echojournal.feature_entries.presentation.state

sealed interface ReplayEchoAction {
    data class Play(val uri: String) : ReplayEchoAction
}