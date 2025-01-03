package nl.codingwithlinda.echojournal.feature_entries.domain.usecase

import nl.codingwithlinda.echojournal.feature_entries.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.entries
import org.junit.Test


class FilterOnMoodAndTopicTest{

    private val filterOnMoodAndTopic = FilterOnMoodAndTopic()
    private val echoes = entries.mapIndexed {i, echo ->
        echo.copy(
            mood = Mood.SAD,
            topics = listOf("Topic $i", "Topic ${i+1}")
        )
    }

    @Test
    fun `test filter on mood and topic - result contains all moods and topics`(){
        val moods = listOf(Mood.SAD, Mood.NEUTRAL)
        val topics = emptyList<String>()

        val res = filterOnMoodAndTopic.filter(echoes, moods, topics)


        res.onEach {
            println(it.topics)

        }

        assert(res.size == 5)
    }


    @Test
    fun `test filter on mood and topic - result contains filtered moods and topics`(){
        val moods = listOf(Mood.SAD, Mood.NEUTRAL)
        val topics = listOf("Topic 1", "Topic 2")

        val res = filterOnMoodAndTopic.filter(echoes, moods, topics)

        res.onEach {
            println(it.topics)
            assert(it.mood in moods)
            assert(it.topics.any { topic -> topic in topics })
        }

        assert(res.size == 3)
    }

    @Test
    fun `test filter on mood and topic - result is empty no mood match`(){
        val moods = listOf(Mood.PEACEFUL)
        val topics = listOf("Topic 1", "Topic 2")

        val res = filterOnMoodAndTopic.filter(echoes, moods, topics)

        assert(res.isEmpty())

        res.onEach {
            println(it.topics)
            assert(it.mood in moods)
            assert(it.topics.any { topic -> topic in topics })
        }
    }

    @Test
    fun `test filter on mood and topic - result is empty no topics match`(){
        val moods = listOf(Mood.SAD)
        val topics = listOf("Topic 10")

        val res = filterOnMoodAndTopic.filter(echoes, moods, topics)

        assert(res.isEmpty())

        res.onEach {
            println(it.topics)
            assert(it.mood in moods)
            assert(it.topics.any { topic -> topic in topics })
        }
    }
}