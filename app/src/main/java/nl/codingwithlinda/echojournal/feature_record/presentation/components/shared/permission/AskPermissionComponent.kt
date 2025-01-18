package nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import nl.codingwithlinda.echojournal.MainActivity

@Composable
fun AskPermissionComponent(
    hasPermission: (Boolean) -> Unit,
) {

    val context = LocalContext.current
    var hasRecordAudioPermission by remember {
        mutableStateOf(true)
    }
    var isPermanentlyDeclined by remember {
        mutableStateOf(false)
    }
    var shouldShowPermanentlyDeclinedDialog by remember {
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
            isPermanentlyDeclined =  !rationale && !hasRecordAudioPermission

            hasPermission(hasRecordAudioPermission)

            shouldShowPermanentlyDeclinedDialog = isPermanentlyDeclined

        }
    }
    /* if (uiState.showPermissionDeclinedDialog){
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
              onPermissionAction(PermissionAction.CloseDialog)
          }
      )
  }*/
    if (shouldShowPermanentlyDeclinedDialog) {
        if (isPermanentlyDeclined) {
            PermissionDeclinedDialog(
                isPermanentlyDeclined = isPermanentlyDeclined,
                onConfirm = {
                    if (isPermanentlyDeclined) {
                        context as MainActivity
                        context.openAppSettings()
                    } else {
                        launcher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                },
                onDismiss = {
                    shouldShowPermanentlyDeclinedDialog = false
                }
            )
        }
    }
}