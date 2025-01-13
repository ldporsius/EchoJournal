package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingMode
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.finite.CounterState
import java.util.Locale

class RecordAudioViewModel(
    val dispatcherProvider: DispatcherProvider,
    val recorder: AudioRecorder,
    val dateTimeFormatter: DateTimeFormatterDuration,
    val echoFactory: EchoFactory,
    val navToCreateEcho: (EchoDto) -> Unit
): ViewModel() {

    private val recordingState = MutableStateFlow(RecordingState.STOPPED)
    private val _uiState = MutableStateFlow(RecordAudioUiState(
        recordingMode = RecordingMode.QUICK
    ))

    val uiState = _uiState.combine(recordingState){ uiState, recording ->
        uiState.copy(
            recordingState = recording
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), _uiState.value)

    private val counterState = CounterState()

    init {
        viewModelScope.launch {
            counterState.counter.collectLatest {
                val durationText = dateTimeFormatter.formatDateTimeMillis(it.toLong())

                _uiState.update {
                    it.copy(
                        duration = durationText ,
                    )
                }
            }
        }
    }

    fun onAction(action: RecordAudioAction) {
        when (action) {
            is RecordAudioAction.ChangeRecordingMode -> {
                _uiState.update {
                    it.copy(
                        recordingMode = action.mode
                    )
                }
            }
            RecordAudioAction.ToggleVisibility -> {
                toggleRecordingState()
            }
            is RecordAudioAction.StartRecording -> {

                recorder.start(System.currentTimeMillis().toString())

                recordingState.update {
                    RecordingState.RECORDING
                }

                simulateWeAreCounting()
            }

            RecordAudioAction.CancelRecording -> {
                recorder.stop()
                recordingState.update {
                    RecordingState.STOPPED
                }
                simulateWeAreCounting()
            }

            RecordAudioAction.PauseRecording -> {
                recorder.pause()
                recordingState.update {
                    RecordingState.PAUSED
                }
               simulateWeAreCounting()

            }
            RecordAudioAction.SaveRecording -> {
                recorder.stop()
                recordingState.update {
                    RecordingState.STOPPED
                }

                simulateWeAreCounting()

                viewModelScope.launch {
                    recorder.listener.firstOrNull()?.let {
                        navToCreateEcho(echoFactory.createEchoDto(it))
                    }
                }
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

    private fun toggleRecordingState(){
        recordingState.update {
            when (it) {
                RecordingState.RECORDING -> RecordingState.PAUSED
                RecordingState.PAUSED -> RecordingState.RECORDING
                RecordingState.STOPPED -> RecordingState.RECORDING
            }
        }

        println("RECORDING STATE = ${recordingState.value}")
    }


    private fun simulateWeAreCounting(){

        CoroutineScope(dispatcherProvider.default).launch{
           counterState.startCounting(recordingState.value)
        }
    }
}