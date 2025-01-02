package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import android.annotation.SuppressLint
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Echo
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.milliseconds

@SuppressLint("NewApi")
object GroupByTimestamp {

    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)

    fun groupByTimestamp(entries: List<Echo>, currentTime: Long = System.currentTimeMillis()): Map<Long, List<Echo>> {

        val entriesByTimestamp: Map<Long, List<Echo>> = entries.groupBy {
          timeDiffAsLong(it.timeStamp, currentTime)
        }

        return entriesByTimestamp
    }

    fun timeDiffAsLong(timeStamp: Long, currentTime: Long): Long {
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

    fun formatDateTime(timestamp: Long, locale: Locale): String {
        val dateFormatterLocale = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale)

        return dateFormatterLocale.format(LocalDateTime.ofEpochSecond(timestamp/1000, 0, ZoneOffset.UTC))
    }

}