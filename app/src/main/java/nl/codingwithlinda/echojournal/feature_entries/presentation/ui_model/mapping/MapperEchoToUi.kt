package nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.mapping

import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import nl.codingwithlinda.echojournal.core.domain.model.Echo
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap
import java.util.Locale

fun Echo.toUi(
    dateTimeFormatter: DateTimeFormatter,
    locale: Locale
): UiEcho{
    return UiEcho(
        id = id,
        mood = mood.toUi(),
        name = title,
        description = description,
        timeStamp = dateTimeFormatter.formatDateTime(timeStamp,locale),
        amplitudes = amplitudes,
        topics = topics.map {
           it.name
        },
        duration = "0:00"
    )
}

fun Mood.toUi(): UiMood {
    return moodToColorMap[this] ?: error("Mood not found")
}