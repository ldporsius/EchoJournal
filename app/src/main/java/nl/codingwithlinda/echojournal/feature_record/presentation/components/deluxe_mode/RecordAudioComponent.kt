package nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import nl.codingwithlinda.echojournal.MainActivity
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission.PermissionDeclinedDialog
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission.openAppSettings
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioUiState
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordingMode
import nl.codingwithlinda.echojournal.ui.theme.labelFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordAudioComponent(
    modifier: Modifier = Modifier,
    uiState: RecordAudioUiState,
    onAction: (RecordAudioAction) -> Unit,
) {
    val recorderModifier = Modifier
        .fillMaxWidth()
        .padding(top = 48.dp, bottom = 72.dp)

    val context = LocalContext.current
    var hasRecordAudioPermission by remember {
        mutableStateOf(true)
    }
    var isPermanentlyDeclined by remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            hasRecordAudioPermission = it
        }
    )

    LaunchedEffect(key1 = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            launcher.launch(android.Manifest.permission.RECORD_AUDIO)
            hasRecordAudioPermission =
                context.checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
            val rationale = shouldShowRequestPermissionRationale(context as MainActivity, android.Manifest.permission.RECORD_AUDIO)
            println("rationale $rationale")
            println("hasRecordAudioPermission $hasRecordAudioPermission")
            isPermanentlyDeclined =  !rationale
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            //onAction(RecordAudioAction.ToggleVisibility)
            onAction(RecordAudioAction.ChangeRecordingMode(RecordingMode.QUICK))
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

    if (uiState.showPermissionDeclinedDialog){
        PermissionDeclinedDialog(
            isPermanentlyDeclined = isPermanentlyDeclined,
            onConfirm = {
                if (isPermanentlyDeclined) {
                    context as MainActivity
                    context.openAppSettings()
                }
                else{
                    launcher.launch(Manifest.permission.RECORD_AUDIO)
                }
            },
            onDismiss = {
                onAction(RecordAudioAction.CloseDialog)
            }
        )
    }
}