package nl.codingwithlinda.echojournal.core.presentation.state

import nl.codingwithlinda.echojournal.core.domain.model.Topic

sealed interface TopicAction {

    data class TopicChanged(val topic: String): TopicAction
    data class ShowHideTopics(val visible: Boolean): TopicAction
    data class SelectTopic(val topic: Topic): TopicAction
    data class CreateTopic(val text: String): TopicAction
    data class RemoveTopic(val topic: Topic): TopicAction
}