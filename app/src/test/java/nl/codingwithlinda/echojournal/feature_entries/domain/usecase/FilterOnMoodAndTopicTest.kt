package nl.codingwithlinda.echojournal.feature_entries.domain.usecase

import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.entries
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeTopics
import org.junit.Test
import com.google.common.truth.Truth.*

class FilterOnMoodAndTopicTest{

    private val filterOnMoodAndTopic = FilterOnMoodAndTopic()
    private val echoes = entries.mapIndexed {i, echo ->
        echo.copy(
            mood = Mood.SAD,
            topics = fakeTopics
        )
    }

    private val testTopics = fakeTopics.take(2)
    @Test
    fun `test filter on mood and topic - result contains all moods and topics`(){
        val moods = listOf(Mood.SAD, Mood.NEUTRAL)
        val topics = emptyList<Topic>()

        val res = filterOnMoodAndTopic.filter(echoes, moods, topics)


        res.onEach {
            println(it.topics)

        }

        assertThat(res).containsExactlyElementsIn(echoes)
    }


    @Test
    fun `test filter on mood and topic - result contains filtered moods and topics`(){
        val moods = listOf(Mood.SAD, Mood.NEUTRAL)
        val topics = testTopics

        val res = filterOnMoodAndTopic.filter(echoes, moods, topics)

        res.onEach {
            println(it.topics)
            assert(it.mood in moods)
            assert(it.topics.any { topic -> topic in topics })
        }

       assertThat(res).containsExactlyElementsIn(echoes)
    }

    @Test
    fun `test filter on mood and topic - result is empty no mood match`(){
        val moods = listOf(Mood.PEACEFUL)
        val topics = testTopics

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
        val moods = listOf(Mood.PEACEFUL)
        val topics = testTopics

        val res = filterOnMoodAndTopic.filter(echoes, moods, topics)

        assertThat(res).isEmpty()

        res.onEach {
            println(it.topics)
            assert(it.mood in moods)
            assert(it.topics.any { topic -> topic in topics })
        }
    }
}