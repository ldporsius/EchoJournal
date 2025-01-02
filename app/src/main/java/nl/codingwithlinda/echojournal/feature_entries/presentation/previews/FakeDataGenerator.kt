package nl.codingwithlinda.echojournal.feature_entries.presentation.previews

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterMedium
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterShort
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Echo
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEchoGroup
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.GroupByTimestamp
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap
import nl.codingwithlinda.echojournal.ui.theme.neutal80
import java.util.Locale
import kotlin.random.Random
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds

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
fun fakeUiEcho(mood: Mood,timestamp: String): UiEcho {
    return UiEcho(
        mood = moodToColorMap.getOrElse(mood){
           defaultUiMood
        },
        name = "Entry 1",
        timeStamp = timestamp,
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

val timestamp = System.currentTimeMillis()
val yesterday = timestamp - 86400000
val older = yesterday.milliseconds.minus(1.days).inWholeMilliseconds
val entries =  listOf(
    fakeEcho(Mood.STRESSED, timestamp = timestamp),
    fakeEcho(Mood.SAD, timestamp = yesterday),
    fakeEcho(Mood.NEUTRAL, timestamp = yesterday),
    fakeEcho(Mood.PEACEFUL, timestamp = yesterday),
    fakeEcho(Mood.EXITED, timestamp = older ),
)
val fakeGroups = GroupByTimestamp.groupByTimestamp(entries).map { listEntry ->

    val headerUI = GroupByTimestamp.timeDiffAsUiText(listEntry.key)

    val formatter : DateTimeFormatter = if(listEntry.key > 1L)
        DateTimeFormatterMedium()
    else
        DateTimeFormatterShort()

    UiEchoGroup(
        header = headerUI,
        entries = listEntry.value.map {
            val timeString = formatter.formatDateTime(it.timeStamp, Locale.getDefault())
            fakeUiEcho(it.mood, timeString)
        }
    )
}