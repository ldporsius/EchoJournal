package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState

class RecordAudioViewModel(

): ViewModel() {


    private val _uiState = MutableStateFlow(RecordAudioUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: RecordAudioAction) {
        when (action) {
            is RecordAudioAction.StartRecording -> {
                _uiState.update {
                    it.copy(
                        isRecording = true
                    )
                }
            }

            RecordAudioAction.CancelRecording -> {

            }

            RecordAudioAction.PauseRecording -> {
                _uiState.update {
                    it.copy(
                        isRecording = false
                    )
                }
            }
            RecordAudioAction.SaveRecording -> Unit

        }
    }
}