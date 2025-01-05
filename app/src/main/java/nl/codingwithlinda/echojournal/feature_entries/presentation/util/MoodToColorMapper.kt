package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.ui.theme.exited80
import nl.codingwithlinda.echojournal.ui.theme.neutal80
import nl.codingwithlinda.echojournal.ui.theme.peaceful80
import nl.codingwithlinda.echojournal.ui.theme.sad80
import nl.codingwithlinda.echojournal.ui.theme.stressed80

val moodToColorMap = mapOf(
    Mood.STRESSED to UiMood(
        mood = Mood.STRESSED,
        icon = R.drawable.mood_stressed,
        color = stressed80.toArgb(),
        name = UiText.StringResource(R.string.stressed)
    ),
    Mood.SAD to UiMood(
        mood = Mood.SAD,
        icon = R.drawable.mood_sad,
        color = sad80.toArgb(),
        name = UiText.StringResource(R.string.sad)
    ),
    Mood.NEUTRAL to UiMood(
        mood = Mood.NEUTRAL,
        icon = R.drawable.mood_neutral,
        color = neutal80.toArgb(),
        name = UiText.StringResource(R.string.neutral)
    ),
    Mood.PEACEFUL to UiMood(
        mood = Mood.PEACEFUL,
        icon = R.drawable.mood_peaceful,
        color = peaceful80.toArgb(),
        name = UiText.StringResource(R.string.peaceful)
    ),
    Mood.EXITED to UiMood(
        mood = Mood.EXITED,
        icon = R.drawable.mood_exited,
        color = exited80.toArgb(),
        name = UiText.StringResource(R.string.excited)
    ),

)