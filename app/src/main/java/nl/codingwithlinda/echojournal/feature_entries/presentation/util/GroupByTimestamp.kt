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
import java.util.Locale
import java.util.concurrent.TimeUnit

object GroupByTimestamp {

    fun groupByTimestamp(entries: List<Echo>, currentTime: Long = System.currentTimeMillis()): Map<Long, List<Echo>> {

        val entriesByTimestamp: Map<Long, List<Echo>> = entries.groupBy {
          timeDiffAsLong(it.timeStamp, currentTime)
        }

        return entriesByTimestamp
    }

    private fun timeDiffAsLong(timeStamp: Long, currentTime: Long): Long {
        val timeDifference = currentTime - timeStamp
        val days = TimeUnit.MILLISECONDS.toDays(timeDifference)

        return days
    }

    fun timeDiffAsUiText(timeDiff: Long): UiText {
       return when{
           timeDiff < 0L -> UiText.StringResource(R.string.future)
           timeDiff == 0L -> UiText.StringResource(R.string.today)
           timeDiff == 1L -> UiText.StringResource(R.string.yesterday)
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