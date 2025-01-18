package nl.codingwithlinda.echojournal.feature_record.presentation.components.shared

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.echojournal.feature_record.domain.AudioRecorder
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission.PermissionAction
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.state.RecordingSharedViewState

class SharedRecordingViewModel(
    private val audioRecorder: AudioRecorder
): ViewModel() {

    private val _uiState = MutableStateFlow(
        RecordingSharedViewState(
        )
    )
    fun handlePermissionAction(action: PermissionAction){
        when(action){
            PermissionAction.OpenDialog ->{
                _uiState.update {
                    it.copy(
                        showPermissionDeclinedDialog = true
                    )
                }
            }
            PermissionAction.CloseDialog -> {
                _uiState.update {
                    it.copy(
                        showPermissionDeclinedDialog = false
                    )
                }
            }
        }
    }

    fun startRecording(){
        audioRecorder.start()
    }
}