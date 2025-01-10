package nl.codingwithlinda.echojournal.core.presentation.util

import kotlin.time.Duration.Companion.milliseconds

class DateTimeFormatterDuration{
    fun formatDateTimeMillis(timestamp: Long): String {

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