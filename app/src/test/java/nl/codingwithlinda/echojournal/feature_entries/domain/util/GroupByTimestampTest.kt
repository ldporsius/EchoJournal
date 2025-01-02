package nl.codingwithlinda.echojournal.feature_entries.domain.util

import nl.codingwithlinda.echojournal.feature_entries.domain.model.Echo
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Mood
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
                mood = Mood.NEUTRAL,
                name = "Entry 1",
                timeStamp = timestamp,
                amplitudes = listOf(0.1f, 0.2f, 0.3f)
            ),

            Echo(
                mood = Mood.NEUTRAL,
                name = "Entry 2",
                timeStamp = timestamp.minus(1.days.inWholeMilliseconds),
                amplitudes = listOf(0.1f, 0.2f, 0.3f)
            ),

            )

        val groupedEntries = GroupByTimestamp.groupByTimestamp(entries, timestamp)

        assertEquals(2, groupedEntries.size)

        println(groupedEntries)
        assertEquals(1, groupedEntries[0L]?.size)
        assertEquals(1, groupedEntries[1L]?.size)
    }
}