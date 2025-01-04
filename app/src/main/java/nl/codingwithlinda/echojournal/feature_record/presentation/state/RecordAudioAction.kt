package nl.codingwithlinda.echojournal.feature_record.presentation.state

sealed interface RecordAudioAction {
    data object OpenDialog : RecordAudioAction
    data object CloseDialog : RecordAudioAction
    data object ToggleVisibility : RecordAudioAction
    data object StartRecording : RecordAudioAction
    data object PauseRecording : RecordAudioAction
    data object CancelRecording : RecordAudioAction
    data object SaveRecording : RecordAudioAction
}