package nl.codingwithlinda.echojournal.core.data

import nl.codingwithlinda.echojournal.core.domain.model.Topic

class TopicFactory {

    fun createTopic(name: String): Topic {
        return Topic(name)
    }
}