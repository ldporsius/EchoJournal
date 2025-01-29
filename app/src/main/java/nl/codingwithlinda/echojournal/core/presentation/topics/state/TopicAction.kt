package nl.codingwithlinda.echojournal.core.presentation.topics.state

import nl.codingwithlinda.core.model.Topic

sealed interface TopicAction {

    data class TopicChanged(val topicText: String): TopicAction
    data class ShowHideTopics(val visible: Boolean): TopicAction
    data class SelectTopic(val topic: Topic): TopicAction
    data class CreateTopic(val text: String): TopicAction
    data class RemoveTopic(val topic: Topic): TopicAction
}