package nl.codingwithlinda.echojournal.core.presentation.util

import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Locale


class DateTimeFormatterShort: DateTimeFormatter {
    private val timeFormatter: java.time.format.DateTimeFormatter =
        java.time.format.DateTimeFormatter.ofPattern("HH:mm", Locale.US)

    override fun formatDateTime(timestamp: Long, locale: Locale): String {
        val localDateTime = LocalDateTime.ofEpochSecond(
            timestamp/1000,
            0,
            ZoneOffset.UTC
        )
        return localDateTime.format(timeFormatter)
    }
}