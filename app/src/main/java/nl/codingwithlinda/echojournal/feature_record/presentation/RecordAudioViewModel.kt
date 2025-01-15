package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.echojournal.core.data.EchoDto
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.core.data.EchoFactory
import nl.codingwithlinda.echojournal.core.di.DispatcherProvider
import nl.codingwithlinda.echojournal.core.domain.util.EchoResult
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingMode
import nl.codingwithlinda.echojournal.feature_record.domain.RecordingState
import nl.codingwithlinda.echojournal.feature_record.domain.error.RecordingFailedError
import nl.codingwithlinda.echojournal.feature_record.presentation.state.finite.CounterState
import nl.codingwithlinda.echojournal.feature_record.util.toUiText

class RecordAudioViewModel(
    val dispatcherProvider: DispatcherProvider,
    val recorder: AudioRecorder,
    val dateTimeFormatter: DateTimeFormatterDuration,
    val echoFactory: EchoFactory,
    val navToCreateEcho: (EchoDto) -> Unit,
    val onRecordingFailed: (error: RecordingFailedError) -> Unit
): ViewModel() {


    private val _uiState = MutableStateFlow(RecordAudioUiState(
        recordingMode = RecordingMode.QUICK
    ))

    val uiState = combine(_uiState, recorder.recorderState){ uiState, recording, ->
        println("UI State has recording state: ${recording.recordingEnum.name}")
        uiState.copy(
            recordingState = recording.recordingEnum
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), _uiState.value)

    private val counterState = CounterState()
    private val recordingResult = recorder.listener

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

        recordingResult.onEach {res ->
            when(res){
                is EchoResult.Error -> {
                   onRecordingFailed(res.error)
                }
                is EchoResult.Success -> {
                    val echo = echoFactory.createEchoDto(res.data)
                    navToCreateEcho(echo)
                }
            }
        }.launchIn(viewModelScope)
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
            }

            RecordAudioAction.onCancelClicked -> {
                recorder.onCancelAction()
            }
            RecordAudioAction.onMainClicked -> {
                recorder.onMainAction()
            }
            RecordAudioAction.onSecondaryClicked -> {
                recorder.onSecondaryAction()
            }


            //permissions/////////////////////////////////////////////////
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