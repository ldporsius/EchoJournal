package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState
import java.util.Locale

class RecordAudioViewModel(
    val recorder: AudioRecorder,
    val dateTimeFormatter: DateTimeFormatter
): ViewModel() {

    private val _uiState = MutableStateFlow(RecordAudioUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            recorder.listener.collect {audioRecorderData ->
                println("amplitude: ${audioRecorderData.amplitude}")
                _uiState.update {
                    it.copy(
                        duration = dateTimeFormatter.formatDateTime(audioRecorderData.duration, Locale.getDefault())
                    )
                }
            }
        }
    }
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
                        isRecording = true,
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
                        isRecording = false,
                    )
                }
                recorder.stop()
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