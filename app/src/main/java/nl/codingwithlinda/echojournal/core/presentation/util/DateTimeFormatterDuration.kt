package nl.codingwithlinda.echojournal.core.presentation.util

import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object DateTimeFormatterDuration{

    private var updateFrequency = 100L

    fun formatDateTimeMillis(timestamp: Long): String {

        return timestamp.milliseconds.toComponents{ minutes, seconds, nanoseconds ->
           val millis = nanoseconds / 1_000_000 / updateFrequency
            val formattedMinutes = minutes.toString().padStart(2, '0')
            val formattedSeconds = seconds.toString().padStart(2, '0')
            val formattedMillis = millis.toString().padStart(2, '0')

            "$formattedMinutes:$formattedSeconds:$formattedMillis"
        }
    }

    fun formatDurationProgress(progress: Float, duration: Long): String{
        //val percentage = progress.toFloat() / duration.toFloat()

        val timePassed = (progress * duration)

        println("DateTimeFormatterDuration: $timePassed")

        val progressText = timePassed.roundToInt().milliseconds.toComponents{
            minutes, seconds, nanoseconds ->
            val formattedMinutes = minutes.toString().padStart(2, '0')
            val formattedSeconds = seconds.toString().padStart(2, '0')
            "$formattedMinutes:$formattedSeconds"
        }
        val durationText = duration.milliseconds.toComponents { minutes, seconds, nanoseconds ->
            val formattedMinutes = minutes.toString().padStart(2, '0')
            val formattedSeconds = seconds.toString().padStart(2, '0')
            "$formattedMinutes:$formattedSeconds"
        }
        return "${progressText}/${durationText}"
    }



}