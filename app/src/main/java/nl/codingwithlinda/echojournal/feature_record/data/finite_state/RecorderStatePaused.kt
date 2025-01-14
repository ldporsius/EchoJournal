package nl.codingwithlinda.echojournal.feature_record.data.finite_state

import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState

class RecorderStatePaused(
    private val audioRecorder: AudioRecorder
): RecorderState {

    override val recordingEnum: RecordingState
        get() = RecordingState.PAUSED

    override fun cancel() {
        audioRecorder.stop()
        audioRecorder.changeState(RecorderStateStopped(audioRecorder))
    }

    override fun main() {
        audioRecorder.resume()
        audioRecorder.changeState(RecorderStateRecording(audioRecorder))
    }

    override fun secondary() {
       audioRecorder.stop()
    }

}