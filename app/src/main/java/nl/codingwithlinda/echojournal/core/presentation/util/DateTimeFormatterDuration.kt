package nl.codingwithlinda.echojournal.core.presentation.util

import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

class DateTimeFormatterDuration: DateTimeFormatter {
    override fun formatDateTime(timestamp: Long, locale: Locale): String {

        return timestamp.milliseconds.toComponents{ minutes, seconds, nanoseconds ->
           val millis = nanoseconds / 1_000_000 / updateFrequency
            val formattedMinutes = minutes.toString().padStart(2, '0')
            val formattedSeconds = seconds.toString().padStart(2, '0')
            val formattedMillis = millis.toString().padStart(2, '0')

            "$formattedMinutes:$formattedSeconds:$formattedMillis"

        }
    }

    companion object{
        var updateFrequency = 100L
    }
}