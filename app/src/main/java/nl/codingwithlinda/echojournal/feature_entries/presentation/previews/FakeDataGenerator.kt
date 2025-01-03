package nl.codingwithlinda.echojournal.feature_entries.presentation.previews

import android.net.Uri
import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterMedium
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterShort
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
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
    mood = Mood.NEUTRAL,
    icon = R.drawable.mood_neutral,
    color = neutal80.toArgb(),
    name = UiText.DynamicString("Neutral")
)
val defaultDescription =
            "Mens sana in corpore sano." +
            "Homo homini lupus est." +
            "Io vivat, Io vivat, nunc est bibendum." +
            "Et ceterum autem censeo Carthaginem delendam esse." +
            "Alea iacta est." +
            "Veni, Vidi, Vici." +
            "Natura artis magistra est."

fun fakeEcho(mood: Mood, timestamp: Long): Echo {
    return Echo(
        mood = mood,
        name = "Entry 1",
        timeStamp = timestamp,
        amplitudes = listOf(0.1f, 0.2f, 0.3f)
    )
}
fun fakeUiEcho(id: String, mood: Mood,timestamp: String): UiEcho {
    return UiEcho(
        id = id,
        mood = moodToColorMap.getOrElse(mood){
            defaultUiMood
        },
        name = "Entry 1",
        description = defaultDescription.take(Random.nextInt(0, defaultDescription.length)),
        timeStamp = timestamp,
        duration = "0:00/12:00",
        amplitudes = fakeAmplitudes(),
        topics = fakeUiTopics(false).take(Random.nextInt(0, 4)).map { it.name }
    )
}
fun fakeUiTopics(isSelected: Boolean): List<UiTopic>{
    return listOf(
        UiTopic(
            name = "Topic 0",
            isSelected = isSelected
        ),
        UiTopic(
            name = "Topic 1",
            isSelected = isSelected
        ),
        UiTopic(
            name = "Topic 2",
            isSelected = isSelected
        ),
        UiTopic(
            name = "Topic 3",
            isSelected = isSelected
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
        entries = listEntry.value.mapIndexed { index, echo ->
            val timeString = formatter.formatDateTime(echo.timeStamp, Locale.getDefault())
            fakeUiEcho(index.toString(), echo.mood, timeString)
        }
    )
}

fun testSound(): Uri{
    return Uri.parse("android.resource://nl.codingwithlinda.echojournal/raw/harp_strum")
}
fun testSound2(): Uri{
    return Uri.parse("android.resource://nl.codingwithlinda.echojournal/raw/newsreportmusic")
}