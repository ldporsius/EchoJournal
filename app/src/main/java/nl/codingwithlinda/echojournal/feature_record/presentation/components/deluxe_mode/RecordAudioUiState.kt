package nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode

import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingMode

data class RecordAudioUiState(
    //val showPermissionDeclinedDialog: Boolean = false,
    //val recordingMode: RecordingMode,
    val recordingState: RecordingState = RecordingState.STOPPED,
    val duration: String = "",
){
    val isRecording: Boolean = recordingState == RecordingState.RECORDING
    val isPaused: Boolean = recordingState == RecordingState.PAUSED

    val title: String = if (isRecording) "Recording your memories ..." else "Recording paused"

    //val shouldShowRecordDeluxeComponent: Boolean = recordingMode == RecordingMode.DELUXE && recordingState != RecordingState.STOPPED

}
