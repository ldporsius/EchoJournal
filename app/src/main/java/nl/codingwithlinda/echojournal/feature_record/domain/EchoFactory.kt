package nl.codingwithlinda.echojournal.feature_record.domain

import nl.codingwithlinda.echojournal.core.data.EchoDto

class EchoFactory {

    fun createEchoDto(audioRecorderData: AudioRecorderData): EchoDto {
        return EchoDto(
            amplitudes = emptyList(),
            uri = audioRecorderData.uri
        )
    }
}