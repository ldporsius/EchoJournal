package nl.codingwithlinda.echojournal.core.presentation.util

import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.FormatStyle
import java.util.Locale


class DateTimeFormatterMedium: DateTimeFormatter {
    private val timeFormatter: java.time.format.DateTimeFormatter =
        java.time.format.DateTimeFormatter.ofPattern("EEEE, MMM dd", Locale.US)

    override fun formatDateTime(timestamp: Long, locale: Locale): String {
        val localDateTime = LocalDateTime.ofEpochSecond(
            timestamp/1000,
            0,
            ZoneOffset.UTC
        )
        return localDateTime.format(timeFormatter)
    }
}

/*
fun formatDateTimeMillis(timestamp: Long, locale: Locale): String {
    val dateFormatterLocale = java.time.format.DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale)

    return dateFormatterLocale.format(LocalDateTime.ofEpochSecond(timestamp/1000, 0, ZoneOffset.UTC))
}*/
