package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingState
import java.util.Locale

class RecordAudioViewModel(
    val recorder: AudioRecorder,
    val dateTimeFormatter: DateTimeFormatterDuration,
    val echoFactory: EchoFactory,
    val navToCreateEcho: (EchoDto) -> Unit
): ViewModel() {

    private val recordingState = MutableStateFlow(RecordingState.STOPPED)
    private val _uiState = MutableStateFlow(RecordAudioUiState())
    val uiState = _uiState.combine(recordingState){ uiState, recording ->
        uiState.copy(
           recordingState = recording
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), _uiState.value)


    fun onAction(action: RecordAudioAction) {
        when (action) {
            RecordAudioAction.ToggleVisibility -> {
                recordingState.update {
                    when (it) {
                        RecordingState.RECORDING -> RecordingState.PAUSED
                        RecordingState.PAUSED -> RecordingState.RECORDING
                        RecordingState.STOPPED -> RecordingState.RECORDING
                    }
                }

                println("RECORDING STATE = ${recordingState.value}")
            }
            is RecordAudioAction.StartRecording -> {

                recorder.start()

                recordingState.update {
                    RecordingState.RECORDING
                }

                var duration = 0L
                viewModelScope.launch {
                    while (
                        recordingState.value == RecordingState.RECORDING
                    ){
                        duration += DateTimeFormatterDuration.updateFrequency
                        val durationText = dateTimeFormatter.formatDateTime(duration, Locale.getDefault())

                        _uiState.update {
                            it.copy(
                                duration = durationText ,
                            )
                        }
                        delay(DateTimeFormatterDuration.updateFrequency)
                    }
                }
            }

            RecordAudioAction.CancelRecording -> {
                recorder.stop()
                recordingState.update {
                    RecordingState.STOPPED
                }
            }

            RecordAudioAction.PauseRecording -> {
                recorder.pause()
                recordingState.update {
                    RecordingState.PAUSED
                }
            }
            RecordAudioAction.SaveRecording -> {
                recorder.stop()
                recordingState.update {
                    RecordingState.STOPPED
                }
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