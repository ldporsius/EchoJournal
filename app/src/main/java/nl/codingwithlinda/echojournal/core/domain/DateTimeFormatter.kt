package nl.codingwithlinda.echojournal.core.domain

import java.util.Locale

interface DateTimeFormatter {
    fun formatDateTime(timestamp: Long, locale: Locale): String
}