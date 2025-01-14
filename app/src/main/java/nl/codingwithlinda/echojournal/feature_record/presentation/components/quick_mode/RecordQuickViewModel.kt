package nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode

import androidx.lifecycle.ViewModel
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder

class RecordQuickViewModel(
    private val audioRecorder: AudioRecorder
): ViewModel() {

    fun handleAction(action: RecordQuickAction){
        when(action){
            RecordQuickAction.CancelRecording -> {
                println("QuikcAction: CancelRecording")
                audioRecorder.cancel()
            }
            RecordQuickAction.SaveRecording -> {
                println("QuikcAction: SaveRecording")
                audioRecorder.stop()
            }
            RecordQuickAction.StartRecording -> {
                println("QuikcAction: StartRecording")
                audioRecorder.start()

            }
        }
    }
}