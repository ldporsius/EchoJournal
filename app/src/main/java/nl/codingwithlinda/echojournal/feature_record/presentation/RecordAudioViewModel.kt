package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
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
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.finite.CounterState

class RecordAudioViewModel(
    val dispatcherProvider: DispatcherProvider,
    val recorder: AudioRecorder,
    val dateTimeFormatter: DateTimeFormatterDuration,
    val echoFactory: EchoFactory,
    val navToCreateEcho: (EchoDto) -> Unit
): ViewModel() {

    //private val recordingState = MutableStateFlow(RecordingState.STOPPED)
    private val _uiState = MutableStateFlow(RecordAudioUiState(
        recordingMode = RecordingMode.QUICK
    ))

    val uiState = _uiState.combine(recorder.recorderState){ uiState, recording ->
        println("UI State has recording state: ${recording.recordingEnum.name}")
        uiState.copy(
            recordingState = recording.recordingEnum
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

            is RecordAudioAction.StartRecording -> {

                recorder.onMainAction()

                simulateWeAreCounting()
            }

            RecordAudioAction.CancelRecording -> {
                recorder.onCancelAction()

                simulateWeAreCounting()
            }

            RecordAudioAction.PauseRecording -> {
                recorder.onSecondaryAction()

               simulateWeAreCounting()

            }
            RecordAudioAction.SaveRecording -> {
                recorder.stop()

                simulateWeAreCounting()

                viewModelScope.launch {
                    recorder.listener.firstOrNull()?.let {
                        navToCreateEcho(echoFactory.createEchoDto(it))
                    }
                }
            }

            ///////////////////////////////////////////////////
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

    private fun simulateWeAreCounting(){

        CoroutineScope(dispatcherProvider.default).launch{
           //counterState.startCounting(recordingState.value)
        }
    }
}