package nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.mapping

import nl.codingwithlinda.echojournal.core.domain.model.Echo
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap

fun Echo.toUi(
    timeStamp: String
): UiEcho{
    return UiEcho(
        id = id,
        uri = uri,
        mood = mood.toUi(),
        name = title,
        description = description,
        timeStamp = timeStamp,
        amplitudes = amplitudes,
        topics = topics,
    )
}

fun Mood.toUi(): UiMood {
    return moodToColorMap[this] ?: error("Mood not found")
}