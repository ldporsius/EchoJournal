package nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode

sealed interface RecordQuickAction {
    data object CancelRecording : RecordQuickAction
    data object MainButtonReleased : RecordQuickAction
    data object MainButtonLongPress : RecordQuickAction
}