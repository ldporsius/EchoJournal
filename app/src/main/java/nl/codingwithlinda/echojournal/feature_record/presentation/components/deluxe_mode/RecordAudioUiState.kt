package nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode

import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingMode

data class RecordAudioUiState(
    val recordingState: RecordingState = RecordingState.STOPPED,
    val duration: String = "",
){
    val isRecording: Boolean = recordingState == RecordingState.RECORDING
    val isPaused: Boolean = recordingState == RecordingState.PAUSED

    val title: String = when(recordingState){
        RecordingState.RECORDING -> {
            "Recording your memories ..."
        }
        RecordingState.PAUSED -> {
            "Recording paused"
        }
        RecordingState.STOPPED -> "Recording stopped"
    }
}
