package nl.codingwithlinda.persistence.data.mapping

import nl.codingwithlinda.core.model.Echo
import nl.codingwithlinda.core.model.Mood
import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.persistence.model.EchoEntity

fun EchoEntity.toDomain(
    amplitudes: List<Float>,
    topics: List<Topic>
): Echo {
    return Echo(
        id = id,
        title = title,
        description = description,
        mood = moodFromInt(mood),
        timeStamp = timestamp,
        uri = soundUri,
        duration = 0L,
        amplitudes = amplitudes,
        topics = topics
    )
}

fun moodFromInt(int: Int): Mood{
    return Mood.entries.find {
           it.sortOrder == int
       } ?: error("Mood not found")

}

fun Echo.toEntity(): EchoEntity {
    return EchoEntity(
        id = id,
        title = title,
        description = description,
        mood = mood.sortOrder,
        timestamp = timeStamp,
        duration = duration,
        soundUri = uri
    )
}