package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterMedium
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterShort
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.core.model.Echo
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEchoGroup
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.mapping.toUi
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale
import java.util.concurrent.TimeUnit

object GroupByTimestamp {

    fun groupByTimestamp(entries: List<Echo>, currentTime: Long = System.currentTimeMillis()): Map<Int, List<Echo>> {

        val entriesByTimestamp: Map<Int, List<Echo>> = entries.groupBy {
          timeDiffAsLong(it.timeStamp, currentTime)
        }

        return entriesByTimestamp
    }

    private fun timeDiffAsLong(timeStamp: Long, currentTime: Long): Int {
        val timeDifference = currentTime - timeStamp
        val now = ZonedDateTime.now()
        val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())
        val days = now.dayOfYear - zonedDateTime.dayOfYear

        println("Timestamp: $timeStamp, Current time: $currentTime")
        println("Time difference: $timeDifference")
        println("Days: $days")
        return days
    }

    private fun timeDiffAsUiText(timeDiff: Int): UiText {
       return when{
           timeDiff < 0 -> UiText.StringResource(R.string.future)
           timeDiff == 0 -> UiText.StringResource(R.string.today)
           timeDiff == 1 -> UiText.StringResource(R.string.yesterday)
           else -> UiText.StringResource(R.string.older)
        }
    }

    fun createGroups(entries: List<Echo>): List<UiEchoGroup>  = groupByTimestamp(entries).map { listEntry ->

        val headerUI = timeDiffAsUiText(listEntry.key)

        val formatter : DateTimeFormatter = if(listEntry.key > 1L)
            DateTimeFormatterMedium()
        else
            DateTimeFormatterShort()

        UiEchoGroup(
            header = headerUI,
            entries = listEntry.value.mapIndexed { index, echo ->
                echo.toUi(
                   timeStamp = formatter.formatDateTime(echo.timeStamp, Locale.getDefault())
                )
            }
        )
    }

}