package nl.codingwithlinda.echojournal.feature_create.presentation.state

sealed interface CreateEchoAction {
    data class TitleChanged(val title: String): CreateEchoAction
}