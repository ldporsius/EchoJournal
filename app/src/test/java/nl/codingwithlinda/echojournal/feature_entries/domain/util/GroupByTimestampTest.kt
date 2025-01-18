package nl.codingwithlinda.echojournal.feature_entries.domain.util

import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.entries
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.GroupByTimestamp
import org.junit.Assert.assertEquals
import org.junit.Test

class GroupByTimestampTest{

   private val timestamp = 1641030400000

    @Test
    fun testGroupByTimestamp(){

        val groupedEntries = GroupByTimestamp.groupByTimestamp(entries, timestamp)

        assertEquals(3, groupedEntries.size)

        println(groupedEntries)
        assertEquals(1, groupedEntries[0L]?.size)
        assertEquals(3, groupedEntries[1L]?.size)
    }
}