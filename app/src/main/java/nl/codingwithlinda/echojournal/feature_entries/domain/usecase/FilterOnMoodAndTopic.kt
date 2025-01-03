package nl.codingwithlinda.echojournal.feature_entries.domain.usecase

import nl.codingwithlinda.echojournal.feature_entries.domain.model.Echo
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Mood

class FilterOnMoodAndTopic {

    fun filter(echoes: List<Echo>, moods: List<Mood>, topics: List<String>): List<Echo>{

        return echoes.filter {
            it.mood in moods && it.topics.any { topic -> topic in topics }
        }

    }
}