package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import java.util.concurrent.TimeUnit

fun mapDateToString(date: Long, currentTime: Long): String {

    val timeDifference = currentTime - date

    val isToday = TimeUnit.MILLISECONDS.toDays(timeDifference) == 0L
    val isYesterday = TimeUnit.MILLISECONDS.toDays(timeDifference) == 1L

    val timeString = when {
        isToday -> "Today"
        isYesterday -> "Yesterday"
        else -> {
            "Older"
        }
    }

    return timeString
}