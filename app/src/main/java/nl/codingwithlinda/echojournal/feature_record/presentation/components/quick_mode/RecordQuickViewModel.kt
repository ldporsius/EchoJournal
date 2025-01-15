package nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder

class RecordQuickViewModel(
    private val audioRecorder: AudioRecorder,
    private val echoFactory: EchoFactory,
    private val onRecordingFinished: (echoDto: EchoDto) -> Unit
): ViewModel() {

    fun handleAction(action: RecordQuickAction){
        when(action){

            RecordQuickAction.CancelRecording -> {
                println("QuikcAction: CancelRecording")
                audioRecorder.cancel()
            }

            RecordQuickAction.MainButtonLongPress -> {
                println("QuikcAction: MainButtonLongPress")
                audioRecorder.start()

            }
            RecordQuickAction.MainButtonReleased -> {
                println("QuikcAction: MainButtonReleased")
                audioRecorder.stop()
                viewModelScope.launch {
                    audioRecorder.listener.firstOrNull()?.let {
                        echoFactory.createEchoDto(it).also {echoDto ->
                            onRecordingFinished(echoDto)
                        }
                    }
                }
            }

        }
    }
}