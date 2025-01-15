package nl.codingwithlinda.echojournal.feature_record.data.finite_state

import nl.codingwithlinda.echojournal.feature_record.data.AndroidMediaRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState

class RecorderStatePaused(
    private val audioRecorder: AndroidMediaRecorder
): RecorderState {

    override val recordingEnum: RecordingState
        get() = RecordingState.PAUSED

    override fun cancel() {
        audioRecorder.cancel()
        audioRecorder.changeState(audioRecorder.stoppedState)
    }

    override fun main() {
        audioRecorder.resume()
        audioRecorder.changeState(audioRecorder.recordingState)
    }

    override fun secondary() {
        audioRecorder.stop()
        audioRecorder.changeState(audioRecorder.stoppedState)

    }

}