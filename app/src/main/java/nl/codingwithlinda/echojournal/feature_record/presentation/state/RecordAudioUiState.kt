package nl.codingwithlinda.echojournal.feature_record.presentation.state

data class RecordAudioUiState(
    val shouldShowRecordAudioComponent: Boolean = false,
    val showPermissionDeclinedDialog: Boolean = false,
    val isRecording: Boolean = true,
    val duration: String = ""
){
    val title: String = if (isRecording) "Recording your memories ..." else "Recording paused"
}
