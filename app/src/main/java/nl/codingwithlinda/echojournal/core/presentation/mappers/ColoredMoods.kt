package nl.codingwithlinda.echojournal.core.presentation.mappers

import nl.codingwithlinda.core.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood

val coloredMoods = Mood.entries
    .sortedBy {
        it.sortOrder
    }
    .associateWith {

        UiMood(
            mood = it,
            icon = it.toColoredIcon(),
            color = it.toColor(),
            name = it.toUiText()
        )
    }
