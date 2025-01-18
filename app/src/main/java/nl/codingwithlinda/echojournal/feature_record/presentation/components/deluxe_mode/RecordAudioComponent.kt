package nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.ui.theme.labelFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordAudioComponent(
    modifier: Modifier = Modifier,
    hasRecordAudioPermission: Boolean,
    requestPermission: () -> Unit,
    uiState: RecordAudioUiState,
    onDismiss: () -> Unit,
    onAction: (RecordDeluxeAction) -> Unit,
) {

    if (!hasRecordAudioPermission){
        requestPermission()
        return
    }

    val recorderModifier = Modifier
        .fillMaxWidth()
        .padding(top = 48.dp, bottom = 72.dp)

    ModalBottomSheet(
        onDismissRequest = {
           onDismiss()
        },
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp
    ) {
        Surface(
            modifier = modifier,
            tonalElevation = 5.dp,
            shadowElevation = 5.dp,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.title,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = uiState.duration,
                    modifier = Modifier,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontFamily = labelFontFamily,
                    style = MaterialTheme.typography.labelSmall
                )

                if (uiState.isRecording) {
                    RecordingActiveComponent(
                        modifier = recorderModifier,
                        onAction = onAction
                    )
                }
                if (uiState.isPaused) {
                    RecordingPausedComponent(
                        modifier = recorderModifier,
                        onAction = onAction,
                    )
                }
            }
        }
    }


}