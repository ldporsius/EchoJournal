package nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model

import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Mood

data class UiMood(
    val mood: Mood,
    val icon: Int,
    val color: Int,
    val name: UiText,
)
