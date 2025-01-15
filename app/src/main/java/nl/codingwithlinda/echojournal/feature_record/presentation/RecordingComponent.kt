package nl.codingwithlinda.echojournal.feature_record.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode.RecordAudioComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode.RecordQuickAction
import nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode.RecordingModeQuickComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingMode

@Composable
fun RecordingComponent(
    modifier: Modifier = Modifier,
    recordAudioUiState: RecordAudioUiState,
    onQuickAction: (RecordQuickAction) -> Unit,
    onRecordAudioAction: (RecordAudioAction) -> Unit,
) {
    Box(
        modifier = modifier

    ) {

        RecordingModeQuickComponent(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
                .padding(16.dp)
            ,
            onAction = onQuickAction,
            onTap = {
                onRecordAudioAction(RecordAudioAction.ChangeRecordingMode(RecordingMode.DELUXE))
                onRecordAudioAction(RecordAudioAction.StartRecording)
            }
        )
    }

    if (recordAudioUiState.shouldShowRecordDeluxeComponent) {
        RecordAudioComponent(
            modifier = Modifier,
            uiState = recordAudioUiState,
            onAction = onRecordAudioAction
        )
    }
}