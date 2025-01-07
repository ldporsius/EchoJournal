package nl.codingwithlinda.echojournal.feature_record.presentation.state

data class RecordAudioUiState(
    val showPermissionDeclinedDialog: Boolean = false,
    val recordingState: RecordingState = RecordingState.STOPPED,
    val duration: String = ""
){
    val isRecording: Boolean = recordingState == RecordingState.RECORDING
    val isPaused: Boolean = recordingState == RecordingState.PAUSED

    val title: String = if (isRecording) "Recording your memories ..." else "Recording paused"

    val shouldShowRecordAudioComponent: Boolean = recordingState != RecordingState.STOPPED
}
