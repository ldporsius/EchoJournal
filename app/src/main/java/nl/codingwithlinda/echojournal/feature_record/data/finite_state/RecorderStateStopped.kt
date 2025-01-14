package nl.codingwithlinda.echojournal.feature_record.data.finite_state

import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.domain.finite_state.RecorderState

class RecorderStateStopped(
        private val audioRecorder: AudioRecorder
): RecorderState {

    override val recordingEnum: RecordingState
        get() = RecordingState.STOPPED

    override fun cancel() {
        audioRecorder.cancel()
        audioRecorder.changeState(RecorderStateStopped(audioRecorder))
    }

    override fun main() {
        audioRecorder.start()
        audioRecorder.changeState(RecorderStateRecording(audioRecorder))
    }

    override fun secondary() {
       //nothing to do
        println("Recording state stopped has no implementation of secondary button click")
    }

}