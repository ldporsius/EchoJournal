package nl.codingwithlinda.echojournal.feature_entries.presentation.previews

import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap
import kotlin.random.Random

fun fakeUiEcho(index: Int): UiEcho {
    return UiEcho(
        mood = moodToColorMap.getOrElse(index){
            moodToColorMap[0]
        },
        name = "Entry 1",
        timeStamp = "12:00",
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