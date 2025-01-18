package nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
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
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingMode
import nl.codingwithlinda.echojournal.feature_record.domain.error.RecordingFailedError
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission.PermissionAction

class RecordAudioViewModel(
    val recorder: AudioRecorder,
    private val recorderInteraction: RecorderInteraction,
    val dateTimeFormatter: DateTimeFormatterDuration,
    val echoFactory: EchoFactory,
    val navToCreateEcho: (EchoDto) -> Unit,
    val onRecordingFailed: (error: RecordingFailedError) -> Unit
): ViewModel() {


    private val _uiState = MutableStateFlow(
        RecordAudioUiState(
    )
    )

    val uiState = combine(_uiState, recorder.recorderState){ uiState, recording, ->
        println("UI State has recording state: ${recording.recordingEnum.name}")
        uiState.copy(
            recordingState = recording.recordingEnum
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), _uiState.value)


    private val recordingResult = recorder.listener

    init {
      viewModelScope.launch {
           recorder.countDuration.collectLatest {
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



    fun onAction(action: RecordDeluxeAction) {

        recorderInteraction.handleDeluxeAction(action).also {
            recorder.handleAction(it)
        }
    }


}