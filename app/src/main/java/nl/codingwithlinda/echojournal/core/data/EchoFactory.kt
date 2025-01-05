package nl.codingwithlinda.echojournal.core.data

import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData

class EchoFactory {

    fun createEchoDto(audioRecorderData: AudioRecorderData): EchoDto {
        return EchoDto(
            amplitudes = emptyList(),
            uri = audioRecorderData.uri
        )
    }


}