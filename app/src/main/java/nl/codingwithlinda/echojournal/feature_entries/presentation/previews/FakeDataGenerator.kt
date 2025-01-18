package nl.codingwithlinda.echojournal.feature_entries.presentation.previews

import android.net.Uri
import nl.codingwithlinda.echojournal.core.domain.model.Echo
import nl.codingwithlinda.echojournal.core.domain.model.EchoTopic
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import java.util.UUID
import kotlin.random.Random
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds


val defaultDescription =
            "Mens sana in corpore sano." +
            "Homo homini lupus est." +
            "Io vivat, Io vivat, nunc est bibendum." +
            "Et ceterum autem censeo Carthaginem delendam esse." +
            "Alea iacta est." +
            "Veni, Vidi, Vici." +
            "Natura artis magistra est."

private const val text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
val fakeTopics = List(5){
    EchoTopic(text.substring(0, 5 + it))
}
fun fakeEcho(mood: Mood, timestamp: Long): Echo {
    return Echo(
        id = "FAKE" + UUID.randomUUID().toString(),
        mood = mood,
        title = "Entry 1",
        description = defaultDescription.take(
            Random.nextInt(
                defaultDescription.length - 1,
                defaultDescription.length
            )
        ),
        timeStamp = timestamp,
        amplitudes = listOf(0.1f, 0.2f, 0.3f),
        duration = 1000,
        topics = listOf( fakeTopics.random(), fakeTopics.random()),
        uri = "",
    )
}


fun fakeAmplitudes(): List<Float>{
    return List(100){
        Random.nextFloat()
    }
}

val timestamp = 1641030400000
val yesterday = timestamp - 86400000
val older = yesterday.milliseconds.minus(1.days).inWholeMilliseconds
val entries =  listOf(
    fakeEcho(Mood.STRESSED, timestamp = timestamp),
    fakeEcho(Mood.SAD, timestamp = yesterday),
    fakeEcho(Mood.NEUTRAL, timestamp = yesterday),
    fakeEcho(Mood.PEACEFUL, timestamp = yesterday),
    fakeEcho(Mood.EXITED, timestamp = older ),
)


fun testSound(): Uri{
    return Uri.parse("android.resource://nl.codingwithlinda.echojournal/raw/harp_strum")
}
fun testSound2(): Uri{
    return Uri.parse("android.resource://nl.codingwithlinda.echojournal/raw/newsreportmusic")
}