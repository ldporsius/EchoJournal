package nl.codingwithlinda.echojournal.core.presentation.util

import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.domain.model.Mood

fun Mood.toBlankIcon(): Int {
    return when (this) {
        Mood.EXITED -> R.drawable.mood_exited_blank
        Mood.PEACEFUL -> R.drawable.mood_peaceful_blank
        Mood.NEUTRAL -> R.drawable.mood_neutral_blank
        Mood.SAD -> R.drawable.mood_sad_blank
        Mood.STRESSED -> R.drawable.mood_stressed_blank
    }
}