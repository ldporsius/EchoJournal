package nl.codingwithlinda.echojournal.feature_entries.presentation.state

sealed interface ReplayEchoAction {
    data class Play(val echoId: String) : ReplayEchoAction
    object Pause : ReplayEchoAction
    object Stop : ReplayEchoAction

}