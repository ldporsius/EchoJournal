package nl.codingwithlinda.echojournal.feature_record.presentation.state

sealed interface RecordAudioAction {
    data object StartRecording : RecordAudioAction
    data object PauseRecording : RecordAudioAction
    data object CancelRecording : RecordAudioAction
    data object SaveRecording : RecordAudioAction
}