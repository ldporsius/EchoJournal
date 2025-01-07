package nl.codingwithlinda.echojournal.feature_entries.domain.usecase

import nl.codingwithlinda.echojournal.core.domain.model.Echo
import nl.codingwithlinda.echojournal.core.domain.model.Mood

class FilterOnMoodAndTopic {

    fun filter(echoes: List<Echo>, moods: List<Mood>, topics: List<String>): List<Echo>{

        if (moods.isEmpty() && topics.isEmpty()){
            return echoes
        }

        if (topics.isEmpty()){
            return echoes.filter {
                it.mood in moods
            }
        }
        if (moods.isEmpty()){
            return echoes.filter {
                it.topics.any { topic -> topic.name in topics }
            }
        }
        return echoes.filter {
            it.mood in moods && it.topics.any { topic -> topic.name in topics }
        }

    }
}