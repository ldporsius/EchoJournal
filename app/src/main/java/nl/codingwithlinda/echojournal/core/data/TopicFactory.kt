package nl.codingwithlinda.echojournal.core.data

import nl.codingwithlinda.core.model.Topic

class TopicFactory {

    fun createTopic(name: String): Topic {
        return Topic(name)
    }
}