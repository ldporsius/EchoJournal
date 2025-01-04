package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState

class RecordAudioViewModel(
    val recorder: AudioRecorder
): ViewModel() {

    private val _uiState = MutableStateFlow(RecordAudioUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: RecordAudioAction) {
        when (action) {
            RecordAudioAction.ToggleVisibility -> {
                _uiState.update {
                    it.copy(
                        shouldShowRecordAudioComponent = !_uiState.value.shouldShowRecordAudioComponent
                    )
                }
            }
            is RecordAudioAction.StartRecording -> {
                _uiState.update {
                    it.copy(
                        isRecording = true
                    )
                }
                recorder.start()
            }

            RecordAudioAction.CancelRecording -> {
                _uiState.update {
                    it.copy(
                        shouldShowRecordAudioComponent = false
                    )
                }
            }

            RecordAudioAction.PauseRecording -> {
                _uiState.update {
                    it.copy(
                        isRecording = false
                    )
                }
            }
            RecordAudioAction.SaveRecording -> {

            }

            RecordAudioAction.CloseDialog -> {
                _uiState.update {
                    it.copy(
                        showPermissionDeclinedDialog = false
                    )
                }
            }
            RecordAudioAction.OpenDialog -> {
                _uiState.update {
                    it.copy(
                        showPermissionDeclinedDialog = true
                    )
                }
            }
        }
    }
}