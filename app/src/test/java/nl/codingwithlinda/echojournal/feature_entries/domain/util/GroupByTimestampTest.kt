package nl.codingwithlinda.echojournal.feature_entries.domain.util

import nl.codingwithlinda.echojournal.core.domain.model.Echo
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.GroupByTimestamp
import org.junit.Assert.*
import org.junit.Test
import kotlin.time.Duration.Companion.days

class GroupByTimestampTest{

   private val timestamp = 1641030400000

    @Test
    fun testGroupByTimestamp(){
        val entries = listOf(
            Echo(
                id = "1",
                mood = Mood.NEUTRAL,
                title = "Entry 1",
                description = "test",
                timeStamp = timestamp,
                amplitudes = listOf(0.1f, 0.2f, 0.3f),
                topics = listOf("Topic 1", "Topic 2")
            ),

            Echo(
                id = "2",
                mood = Mood.NEUTRAL,
                title = "Entry 2",
                description = "test",
                timeStamp = timestamp.minus(1.days.inWholeMilliseconds),
                amplitudes = listOf(0.1f, 0.2f, 0.3f),
                topics = listOf("Topic 1", "Topic 2")
            ),

            )

        val groupedEntries = GroupByTimestamp.groupByTimestamp(entries, timestamp)

        assertEquals(2, groupedEntries.size)

        println(groupedEntries)
        assertEquals(1, groupedEntries[0L]?.size)
        assertEquals(1, groupedEntries[1L]?.size)
    }
}