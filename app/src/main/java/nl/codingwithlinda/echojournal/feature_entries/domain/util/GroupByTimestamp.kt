package nl.codingwithlinda.echojournal.feature_entries.domain.util

import android.annotation.SuppressLint
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Echo
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NewApi")
object GroupByTimestamp {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)

    fun groupByTimestamp(entries: List<Echo>): Map<String, List<Echo>> {

        val entriesByTimestamp = entries.groupBy {
            val localDateTime = LocalDateTime.ofEpochSecond(
                it.timeStamp/1000,
                0,
                ZoneOffset.UTC
                )

            localDateTime.format(formatter)

        }

        return entriesByTimestamp
    }

}