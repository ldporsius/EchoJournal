package nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.mapping

import nl.codingwithlinda.core.model.Echo
import nl.codingwithlinda.echojournal.core.presentation.mappers.toUi
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho

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

