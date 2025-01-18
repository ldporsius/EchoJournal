package nl.codingwithlinda.echojournal.feature_record.domain

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.echojournal.core.domain.util.EchoResult
import nl.codingwithlinda.echojournal.feature_record.domain.error.RecordingFailedError
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.RecordAudioAction

interface AudioRecorder {

    fun handleAction(action: RecordAudioAction)
    val listener: Flow<EchoResult<AudioRecorderData, RecordingFailedError>>
    val countDuration: Flow<Long>

    fun start()
    fun pause()
    fun resume()
    fun stop()
    fun cancel()

    fun changeState(state: RecorderState): RecorderState
    val recorderState: Flow<RecorderState>
}