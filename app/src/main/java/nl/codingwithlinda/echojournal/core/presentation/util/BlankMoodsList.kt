package nl.codingwithlinda.echojournal.core.presentation.util

import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood

val blankMoods = Mood.entries.map {

    it to
    UiMood(
        mood = it,
        icon = it.toBlankIcon(),
        color = it.toColor(),
        name = it.toUiText()
    )
}.toMap()