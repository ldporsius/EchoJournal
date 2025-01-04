package nl.codingwithlinda.echojournal.feature_record.domain

import kotlinx.coroutines.flow.StateFlow

interface AudioRecorder {
    val listener: StateFlow<AudioRecorderData>
    fun start()
    fun stop()
}