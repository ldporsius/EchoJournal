package nl.codingwithlinda.echojournal.feature_record.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState

@Composable
fun RecordAudioComponent(
    modifier: Modifier = Modifier,
    uiState: RecordAudioUiState,
    onAction: (RecordAudioAction) -> Unit
) {
    val recorderModifier = Modifier
        .fillMaxWidth()
        .padding(top = 48.dp, bottom = 72.dp)



    Surface(
        modifier = modifier,
        tonalElevation = 5.dp,
        shadowElevation = 5.dp,
        color = MaterialTheme.colorScheme.surfaceBright
    ) {
        Column(
           modifier =  Modifier.fillMaxWidth(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(text = uiState.title,
                style = MaterialTheme.typography.titleLarge)

            Text(text = uiState.duration,
                style = MaterialTheme.typography.labelSmall)

            when (uiState.isRecording) {
                true -> RecordingActiveComponent(
                    modifier = recorderModifier,
                    onAction = onAction
                )
                false -> RecordingPausedComponent(
                    modifier = recorderModifier,
                    onAction = onAction,
                    showPermissionDeclinedDialog = uiState.showPermissionDeclinedDialog
                )
            }

        }
    }
}