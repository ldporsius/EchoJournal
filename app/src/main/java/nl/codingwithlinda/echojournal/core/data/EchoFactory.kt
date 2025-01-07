package nl.codingwithlinda.echojournal.core.data

import nl.codingwithlinda.echojournal.core.domain.model.Echo
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import java.util.UUID

class EchoFactory {

    fun createEchoDto(audioRecorderData: AudioRecorderData): EchoDto {
        return EchoDto(
            amplitudes = emptyList(),
            uri = audioRecorderData.uri
        )
    }

    fun createEcho(
        echoDto: EchoDto,
        topics: List<Topic>,
        title: String,
        description: String,
        mood: Mood
    ): Echo{
        return Echo(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            topics = topics,
            mood = mood,
            uri = echoDto.uri,
            timeStamp = System.currentTimeMillis(),
            amplitudes = echoDto.amplitudes,
        )
    }
}