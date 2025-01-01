package nl.codingwithlinda.echojournal.feature_entries.presentation.previews

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Echo
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap
import nl.codingwithlinda.echojournal.ui.theme.neutal80
import java.sql.Timestamp
import kotlin.random.Random

val defaultUiMood = UiMood(
    icon = R.drawable.mood_neutral,
    color = neutal80.toArgb()
)
fun fakeEcho(mood: Mood, timestamp: Long): Echo {
    return Echo(
        mood = mood,
        name = "Entry 1",
        timeStamp = timestamp,
        amplitudes = listOf(0.1f, 0.2f, 0.3f)
    )
}
fun fakeUiEcho(mood: Mood,timestamp: Long ): UiEcho {
    return UiEcho(
        mood = moodToColorMap.getOrElse(mood){
           defaultUiMood
        },
        name = "Entry 1",
        timeStamp = timestamp.toString(),
        duration = "0:00/12:00",
        amplitudes = fakeAmplitudes(),
        topics = listOf(
            "family"
        )
    )
}
fun fakeUiTopics(): List<UiTopic>{
    return listOf(
        UiTopic(
            name = "Topic 0",
            isSelected = true
        ),
        UiTopic(
            name = "Topic 1",
            isSelected = true
        ),
        UiTopic(
            name = "Topic 2",
            isSelected = false
        ),
        UiTopic(
            name = "Topic 3",
            isSelected = true
        ),
    )
}

fun fakeAmplitudes(): List<Float>{

    return List(100){
        Random.nextFloat()
    }
}