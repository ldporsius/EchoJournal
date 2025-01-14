package nl.codingwithlinda.echojournal.feature_record.domain.finite_state

import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState

interface RecorderState {
    val recordingEnum : RecordingState
    fun cancel()
    fun main()
    fun secondary()
}
