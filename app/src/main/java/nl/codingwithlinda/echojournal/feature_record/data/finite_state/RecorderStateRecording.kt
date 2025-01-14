package nl.codingwithlinda.echojournal.feature_record.data.finite_state

import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState

class RecorderStateRecording(
    private val audioRecorder: AudioRecorder
): RecorderState {

    override val recordingEnum: RecordingState
        get() = RecordingState.RECORDING

    override fun cancel() {
        audioRecorder.cancel()
    }

    override fun main() {
        audioRecorder.pause()
        audioRecorder.changeState(RecorderStatePaused(audioRecorder))
    }

    override fun secondary() {
        audioRecorder.stop()
        audioRecorder.changeState(RecorderStateStopped(audioRecorder))
    }

}