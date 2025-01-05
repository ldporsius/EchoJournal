package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.domain.DateTimeFormatter
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.domain.EchoFactory
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState
import java.util.Locale

class RecordAudioViewModel(
    val recorder: AudioRecorder,
    val dateTimeFormatter: DateTimeFormatter,
    val echoFactory: EchoFactory,
    val navToCreateEcho: (EchoDto) -> Unit
): ViewModel() {

    private val _uiState = MutableStateFlow(RecordAudioUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            recorder.listener.collect {audioRecorderData ->

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

                var duration = 0L
                viewModelScope.launch {
                    while (
                        uiState.value.isRecording
                    ){
                        duration += 100
                        _uiState.update {
                            it.copy(
                                duration = dateTimeFormatter.formatDateTime(duration, Locale.getDefault()),
                            )
                        }
                        delay(100)
                    }
                }
            }

            RecordAudioAction.CancelRecording -> {
                _uiState.update {
                    it.copy(
                        shouldShowRecordAudioComponent = false
                    )
                }
                recorder.stop()
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
                    navToCreateEcho(echoFactory.createEchoDto(recorder.listener.value))
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