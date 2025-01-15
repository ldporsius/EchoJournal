package nl.codingwithlinda.echojournal.feature_record.data.finite_state

import nl.codingwithlinda.echojournal.feature_record.data.AndroidMediaRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState

class RecorderStateStopped(
        private val audioRecorder: AndroidMediaRecorder
): RecorderState {

    override val recordingEnum: RecordingState
        get() = RecordingState.STOPPED

    override fun cancel() {
        audioRecorder.cancel()
        audioRecorder.changeState(audioRecorder.stoppedState)
    }

    override fun main() {
        audioRecorder.start()
        audioRecorder.changeState(audioRecorder.recordingState)
    }

    override fun secondary() {
       //nothing to do
        println("Recording state stopped has no implementation of secondary button click")
    }

}