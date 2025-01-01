package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.ui.theme.exited80
import nl.codingwithlinda.echojournal.ui.theme.neutal80
import nl.codingwithlinda.echojournal.ui.theme.peaceful80
import nl.codingwithlinda.echojournal.ui.theme.sad80
import nl.codingwithlinda.echojournal.ui.theme.stressed80

val moodToColorMap = mapOf(
    Mood.STRESSED to UiMood(
        icon = R.drawable.mood_stressed,
        color = stressed80.toArgb()
    ),
    Mood.SAD to UiMood(
        icon = R.drawable.mood_sad,
        color = sad80.toArgb()
    ),
    Mood.NEUTRAL to UiMood(
        icon = R.drawable.mood_neutral,
        color = neutal80.toArgb()
    ),
    Mood.PEACEFUL to UiMood(
        icon = R.drawable.mood_peaceful,
        color = peaceful80.toArgb()
    ),
    Mood.EXITED to UiMood(
        icon = R.drawable.mood_exited,
        color = exited80.toArgb()
    ),

)