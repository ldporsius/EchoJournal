package nl.codingwithlinda.echojournal.core.data

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import nl.codingwithlinda.core.model.Echo
import nl.codingwithlinda.core.model.Mood
import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.echojournal.core.domain.util.ECHO_JOURNAL_DIR
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorderData
import java.io.File
import java.io.FileOutputStream
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
    ): Echo {
        val id = UUID.randomUUID().toString()
        val uri = "$id.mp3"
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

    fun persistEcho(source: String, target: String) {
        val sourceDir = File(context.filesDir, ECHO_JOURNAL_DIR)
        val sourceUri = File(sourceDir, source).toUri()
        println("EchoFactory has sourceUri: $sourceUri")

        val file = File(sourceDir, target)

        val output = FileOutputStream(file)
        context.contentResolver.openInputStream(sourceUri)?.use { input ->
            output.use { out ->
                input.copyTo(out)
            }
        }
    }
}