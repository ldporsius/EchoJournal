package nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode

import nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode.RecordQuickAction
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.RecordAudioAction

class RecorderInteraction {
    fun handleDeluxeAction(action: RecordDeluxeAction): RecordAudioAction {
       return transformDeluxeAction(action)
    }
    fun handleQuickAction(action: RecordQuickAction): RecordAudioAction{
        return transformQuickAction(action)
    }

    private fun transformDeluxeAction(action: RecordDeluxeAction): RecordAudioAction{
        return when(action){
            RecordDeluxeAction.ResumeRecording -> RecordAudioAction.onMainClicked
            RecordDeluxeAction.CancelRecording -> RecordAudioAction.onCancelClicked
            RecordDeluxeAction.PauseRecording -> RecordAudioAction.onSecondaryClicked
            RecordDeluxeAction.SaveRecording -> RecordAudioAction.onMainClicked

        }
    }

    private fun transformQuickAction(action: RecordQuickAction): RecordAudioAction{
        return when(action){
            RecordQuickAction.CancelRecording -> RecordAudioAction.onCancelClicked
            RecordQuickAction.MainButtonLongPress -> RecordAudioAction.onMainClicked
            RecordQuickAction.MainButtonReleased -> RecordAudioAction.onMainClicked
        }
    }
}