package nl.codingwithlinda.echojournal.feature_record.presentation.components.shared

interface RecordAudioAction {

    data object onCancelClicked : RecordAudioAction
    data object onMainClicked : RecordAudioAction
    data object onSecondaryClicked : RecordAudioAction

}