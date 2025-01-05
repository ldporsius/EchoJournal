package nl.codingwithlinda.echojournal.feature_create.presentation.state

sealed interface CreateEchoAction {
    data class TitleChanged(val title: String): CreateEchoAction
    data class TopicChanged(val topic: String): CreateEchoAction
    data class ShowHideTopics(val visible: Boolean): CreateEchoAction
    data class SelectTopic(val topic: String): CreateEchoAction
    data class CreateTopic(val topic: String): CreateEchoAction
}