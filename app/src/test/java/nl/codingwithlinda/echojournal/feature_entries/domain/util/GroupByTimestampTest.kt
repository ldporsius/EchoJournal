package nl.codingwithlinda.echojournal.feature_entries.domain.util

import nl.codingwithlinda.echojournal.core.domain.model.Echo
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.entries
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.GroupByTimestamp
import org.junit.Assert.*
import org.junit.Test
import kotlin.enums.enumEntries
import kotlin.time.Duration.Companion.days

class GroupByTimestampTest{

   private val timestamp = 1641030400000

    @Test
    fun testGroupByTimestamp(){

        val groupedEntries = GroupByTimestamp.groupByTimestamp(entries, timestamp)

        assertEquals(2, groupedEntries.size)

        println(groupedEntries)
        assertEquals(1, groupedEntries[0L]?.size)
        assertEquals(1, groupedEntries[1L]?.size)
    }
}