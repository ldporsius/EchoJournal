package nl.codingwithlinda.echojournal.feature_record.presentation.state

data class RecordAudioUiState(
    val shouldShowRecordAudioComponent: Boolean = false,
    val isRecording: Boolean = false,
    val title: String = "",
    val duration: String = ""
)
