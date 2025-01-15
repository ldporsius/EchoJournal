package nl.codingwithlinda.echojournal.feature_record.presentation.state

sealed interface RecordAudioAction {
    data object OpenDialog : RecordAudioAction
    data object CloseDialog : RecordAudioAction
    data class ChangeRecordingMode(val mode: RecordingMode) : RecordAudioAction
    data object StartRecording : RecordAudioAction

    data object onCancelClicked : RecordAudioAction
    data object onMainClicked : RecordAudioAction
    data object onSecondaryClicked : RecordAudioAction

    //data object PauseRecording : RecordAudioAction
    //data object CancelRecording : RecordAudioAction
    //data object SaveRecording : RecordAudioAction
}