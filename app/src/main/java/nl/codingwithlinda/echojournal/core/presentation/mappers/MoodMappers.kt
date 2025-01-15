package nl.codingwithlinda.echojournal.core.presentation.mappers

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.ui.theme.exited80
import nl.codingwithlinda.echojournal.ui.theme.neutal80
import nl.codingwithlinda.echojournal.ui.theme.peaceful80
import nl.codingwithlinda.echojournal.ui.theme.sad80
import nl.codingwithlinda.echojournal.ui.theme.stressed80

fun Mood.toUi(): UiMood {
    return coloredMoods[this] ?: error("Mood not found")
}

fun Mood.toColor(): Int {
    return when (this) {
        Mood.EXITED -> exited80.toArgb()
        Mood.PEACEFUL -> peaceful80.toArgb()
        Mood.NEUTRAL -> neutal80.toArgb()
        Mood.SAD -> sad80.toArgb()
        Mood.STRESSED -> stressed80.toArgb()
    }
}
fun Mood.toColoredIcon(): Int {
    return when (this) {
        Mood.EXITED -> R.drawable.mood_exited
        Mood.PEACEFUL -> R.drawable.mood_peaceful
        Mood.NEUTRAL -> R.drawable.mood_neutral
        Mood.SAD -> R.drawable.mood_sad
        Mood.STRESSED -> R.drawable.mood_stressed
    }
}

fun Mood.toBlankIcon(): Int {
    return when (this) {
        Mood.EXITED -> R.drawable.mood_exited_blank
        Mood.PEACEFUL -> R.drawable.mood_peaceful_blank
        Mood.NEUTRAL -> R.drawable.mood_neutral_blank
        Mood.SAD -> R.drawable.mood_sad_blank
        Mood.STRESSED -> R.drawable.mood_stressed_blank
    }
}

fun Mood.toUiText(): UiText {
    return when (this) {
        Mood.EXITED -> UiText.StringResource(R.string.excited)
        Mood.PEACEFUL -> UiText.StringResource(R.string.peaceful)
        Mood.NEUTRAL -> UiText.StringResource(R.string.neutral)
        Mood.SAD -> UiText.StringResource(R.string.sad)
        Mood.STRESSED -> UiText.StringResource(R.string.stressed)
    }
}