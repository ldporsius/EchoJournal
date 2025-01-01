package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.ui.theme.exited80
import nl.codingwithlinda.echojournal.ui.theme.neutal80
import nl.codingwithlinda.echojournal.ui.theme.peaceful80
import nl.codingwithlinda.echojournal.ui.theme.sad80
import nl.codingwithlinda.echojournal.ui.theme.stressed80

val moodToColorMap = listOf(
    UiMood(
        icon = R.drawable.mood_stressed,
        color = stressed80.toArgb()
    ),
    UiMood(
        icon = R.drawable.mood_sad,
        color = sad80.toArgb()
    ),
    UiMood(
        icon = R.drawable.mood_neutral,
        color = neutal80.toArgb()
    ),
    UiMood(
        icon = R.drawable.mood_peaceful,
        color = peaceful80.toArgb()
    ),
    UiMood(
        icon = R.drawable.mood_exited,
        color = exited80.toArgb()
    ),

)