package nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.mapping

import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import nl.codingwithlinda.echojournal.core.domain.model.Echo
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap
import java.util.Locale

fun Echo.toUi(
    dateTimeFormatter: DateTimeFormatter,
    durationFormatter: DateTimeFormatterDuration,
    locale: Locale
): UiEcho{
    return UiEcho(
        id = id,
        uri = uri,
        mood = mood.toUi(),
        name = title,
        description = description,
        timeStamp = dateTimeFormatter.formatDateTime(timeStamp,locale),
        amplitudes = amplitudes,
        topics = topics,
        duration = durationFormatter.formatDateTimeMillis(duration)
    )
}

fun Mood.toUi(): UiMood {
    return moodToColorMap[this] ?: error("Mood not found")
}