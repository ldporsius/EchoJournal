package nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.state

data class RecordingSharedViewState(
    val showPermissionDeclinedDialog: Boolean = false,
    val hasRecordAudioPermission: Boolean = true,
)
