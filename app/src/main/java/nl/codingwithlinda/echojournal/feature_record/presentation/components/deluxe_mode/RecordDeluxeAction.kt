package nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode

sealed interface RecordDeluxeAction {
    data object PauseRecording : RecordDeluxeAction
    data object ResumeRecording : RecordDeluxeAction
    data object CancelRecording : RecordDeluxeAction
    data object SaveRecording : RecordDeluxeAction
}