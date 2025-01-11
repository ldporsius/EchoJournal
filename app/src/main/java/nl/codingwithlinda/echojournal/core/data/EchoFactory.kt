package nl.codingwithlinda.echojournal.core.data

import android.content.Context
import android.net.Uri
import nl.codingwithlinda.echojournal.core.domain.model.Echo
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import java.io.File
import java.util.UUID

class EchoFactory(
    private val context: Context
) {

    fun createEchoDto(audioRecorderData: AudioRecorderData): EchoDto {
        return EchoDto(
            duration = audioRecorderData.duration,
            uri = audioRecorderData.uri,
            amplitudesUri = audioRecorderData.amplitudesUri
        )
    }

    fun createEcho(
        echoDto: EchoDto,
        amplitudes: List<Float>,
        topics: List<Topic>,
        title: String,
        description: String,
        mood: Mood
    ): Echo{
        val id = UUID.randomUUID().toString()
        val uri = File(context.filesDir, id).path
        return Echo(
            id = id,
            title = title,
            description = description,
            topics = topics,
            mood = mood,
            uri =  uri,
            timeStamp = System.currentTimeMillis(),
            duration = echoDto.duration,
            amplitudes = amplitudes,
        )
    }

    fun persistEcho(path: String){
        val uri = Uri.parse(path)
        context.contentResolver.openOutputStream(uri)?.use {

        }

    }
}