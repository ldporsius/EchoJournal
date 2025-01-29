package nl.codingwithlinda.echojournal.feature_entries.domain.usecase

import nl.codingwithlinda.core.model.Echo
import nl.codingwithlinda.core.model.Mood
import nl.codingwithlinda.core.model.Topic

class FilterOnMoodAndTopic {

    fun filter(echoes: List<Echo>, moods: List<Mood>, topics: List<Topic>): List<Echo>{

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
                it.topics.any { topic -> topic in topics }
            }
        }
        return echoes.filter {
            it.mood in moods && it.topics.any { topic -> topic in topics }
        }

    }
}