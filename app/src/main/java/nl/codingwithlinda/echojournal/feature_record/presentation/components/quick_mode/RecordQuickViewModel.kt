package nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.domain.util.EchoResult
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.error.RecordingFailedError
import nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode.RecorderInteraction

class RecordQuickViewModel(
    private val audioRecorder: AudioRecorder,
    private val recorderInteraction: RecorderInteraction,
    private val echoFactory: EchoFactory,
    private val onRecordingFinished: (echoDto: EchoDto) -> Unit,
    private val onRecordingFailed: (error: RecordingFailedError) -> Unit
): ViewModel() {

    fun handleAction(action: RecordQuickAction){

        recorderInteraction.handleQuickAction(action).also {
            audioRecorder.handleAction(it)
        }
        when(action){

            RecordQuickAction.CancelRecording -> {
                println("QuikcAction: CancelRecording")
                //audioRecorder.cancel()
            }

            RecordQuickAction.MainButtonLongPress -> {
                println("QuikcAction: MainButtonLongPress")
                //audioRecorder.onMainAction()

            }
            RecordQuickAction.MainButtonReleased -> {
                println("QuikcAction: MainButtonReleased")
                //audioRecorder.onMainAction()
                viewModelScope.launch {
                    audioRecorder.listener.firstOrNull()?.let { res ->
                        when(res){
                            is EchoResult.Error -> {
                               onRecordingFailed(res.error)
                            }
                            is EchoResult.Success -> {
                                echoFactory.createEchoDto(res.data).also {echoDto ->
                                    onRecordingFinished(echoDto)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}