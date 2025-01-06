package nl.codingwithlinda.echojournal.core.presentation.util

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.ui.theme.exited80
import nl.codingwithlinda.echojournal.ui.theme.neutal80
import nl.codingwithlinda.echojournal.ui.theme.peaceful80
import nl.codingwithlinda.echojournal.ui.theme.sad80
import nl.codingwithlinda.echojournal.ui.theme.stressed80

fun Mood.toColor(): Int {
    return when (this) {
        Mood.EXITED -> exited80.toArgb()
        Mood.PEACEFUL -> peaceful80.toArgb()
        Mood.NEUTRAL -> neutal80.toArgb()
        Mood.SAD -> sad80.toArgb()
        Mood.STRESSED -> stressed80.toArgb()
    }
}