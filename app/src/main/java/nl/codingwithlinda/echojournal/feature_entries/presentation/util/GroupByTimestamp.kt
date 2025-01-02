package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import android.annotation.SuppressLint
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Echo
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit

@SuppressLint("NewApi")
object GroupByTimestamp {

    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)

    fun groupByTimestamp(entries: List<Echo>, currentTime: Long = System.currentTimeMillis()): Map<UiText, List<Echo>> {

        val entriesByTimestamp: Map<UiText, List<Echo>> = entries.groupBy {
          val days = timeDiffAsLong(it.timeStamp, currentTime)
           when(days){
               0L -> UiText.StringResource(R.string.today)
               1L -> UiText.StringResource(R.string.yesterday)
               else -> UiText.StringResource(R.string.older)
           }
        }

        return entriesByTimestamp
    }

    fun timeDiffAsLong(timeStamp: Long, currentTime: Long): Long {
        val timeDifference = currentTime - timeStamp
        val days = TimeUnit.MILLISECONDS.toDays(timeDifference)

        return days
    }

    fun formatTimeStamp(timestamp: Long): String {
        val localDateTime = LocalDateTime.ofEpochSecond(
            timestamp/1000,
            0,
            ZoneOffset.UTC
        )
        return localDateTime.format(timeFormatter)

    }
}