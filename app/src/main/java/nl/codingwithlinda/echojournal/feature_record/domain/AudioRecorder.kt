package nl.codingwithlinda.echojournal.feature_record.domain

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.echojournal.core.domain.util.EchoResult
import nl.codingwithlinda.echojournal.feature_record.domain.error.RecordingFailedError
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState

interface AudioRecorder {

    val listener: Flow<EchoResult<AudioRecorderData, RecordingFailedError>>
    fun onCancelAction()
    fun onMainAction()
    fun onSecondaryAction()

    fun start()
    fun pause()
    fun resume()
    fun stop()
    fun cancel()

    fun changeState(state: RecorderState): RecorderState
    val recorderState: Flow<RecorderState>
}