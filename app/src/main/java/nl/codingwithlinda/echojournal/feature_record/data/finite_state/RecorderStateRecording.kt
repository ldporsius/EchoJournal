package nl.codingwithlinda.echojournal.feature_record.data.finite_state

import nl.codingwithlinda.echojournal.feature_record.data.AndroidMediaRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState

class RecorderStateRecording(
    private val audioRecorder: AndroidMediaRecorder
): RecorderState {

    override val recordingEnum: RecordingState
        get() = RecordingState.RECORDING

    override fun cancel() {
        audioRecorder.cancel()
        audioRecorder.changeState(audioRecorder.stoppedState)
    }

    override fun main() {
        audioRecorder.stop()
        audioRecorder.changeState(audioRecorder.stoppedState)
    }

    override fun secondary() {
        println("RecorderStateRecording.secondary()")
        audioRecorder.pause()
        audioRecorder.changeState(audioRecorder.pausedState)
    }

}