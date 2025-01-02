package nl.codingwithlinda.echojournal.core.presentation.util

import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.timestamp
import org.junit.Assert.*
import org.junit.Test
import java.util.Locale

class DateTimeFormatterMediumTest{

    @Test
    fun `test date formatter`(){
       val res =  DateTimeFormatterMedium().formatDateTime(
            timestamp = 1641030400000,
            locale = Locale.getDefault()
        )

        println(res)
    }
}