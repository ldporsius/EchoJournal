package nl.codingwithlinda.echojournal.core.presentation.util

import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.domain.model.Mood

fun Mood.toUiText(): UiText {
    return when (this) {
        Mood.EXITED -> UiText.StringResource(R.string.excited)
        Mood.PEACEFUL -> UiText.StringResource(R.string.peaceful)
        Mood.NEUTRAL -> UiText.StringResource(R.string.neutral)
        Mood.SAD -> UiText.StringResource(R.string.sad)
        Mood.STRESSED -> UiText.StringResource(R.string.stressed)
    }
}