package nl.codingwithlinda.echojournal.feature_record.domain

import kotlinx.coroutines.flow.Flow

interface AudioRecorder {

    val listener: Flow<AudioRecorderData>
    fun start(path: String)
    fun pause()
    fun stop()

}