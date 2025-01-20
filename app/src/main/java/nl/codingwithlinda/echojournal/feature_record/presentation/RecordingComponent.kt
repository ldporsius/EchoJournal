package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode.RecordAudioComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode.RecordAudioUiState
import nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode.RecordDeluxeAction
import nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode.RecordQuickAction
import nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode.RecordingModeQuickComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission.AskPermissionComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingMode

@Composable
fun RecordingComponent(
    modifier: Modifier = Modifier,
    recordAudioUiState: RecordAudioUiState,
    onQuickAction: (RecordQuickAction) -> Unit,
    onRecordDeluxeAction: (RecordDeluxeAction) -> Unit,
    startRecording: () -> Unit,
) {
    var hasRecordAudioPermission by remember {
        mutableStateOf(false)
    }
    var recordingMode by remember {
        mutableStateOf(RecordingMode.QUICK)
    }

    var shouldAskPermission by remember {
        mutableStateOf(true)
    }

    if (shouldAskPermission){
        AskPermissionComponent(
            hasPermission = {
                hasRecordAudioPermission = it
                shouldAskPermission = false
            }
        )
    }
    Box(
        modifier = modifier

    ) {

        RecordingModeQuickComponent(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
                .padding(16.dp)
            ,
            hasRecordAudioPermission = hasRecordAudioPermission,
            requestPermission = {
                shouldAskPermission = true
            },
            onAction = onQuickAction,
            onTap = {
                recordingMode = RecordingMode.DELUXE
                startRecording()
            }
        )
    }

    if (recordingMode == RecordingMode.DELUXE) {
        RecordAudioComponent(
            modifier = Modifier,
            hasRecordAudioPermission = hasRecordAudioPermission,
            requestPermission = {
                shouldAskPermission = true
            },
            uiState = recordAudioUiState,
            onAction = onRecordDeluxeAction,
            onDismiss = {
                recordingMode = RecordingMode.QUICK
            },

            )
    }


}