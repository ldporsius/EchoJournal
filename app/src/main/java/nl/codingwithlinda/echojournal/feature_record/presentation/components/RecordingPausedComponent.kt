package nl.codingwithlinda.echojournal.feature_record.presentation.components

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import nl.codingwithlinda.echojournal.MainActivity
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.ui.theme.primary50

@Composable
fun RecordingPausedComponent(
    modifier: Modifier = Modifier,
    onAction: (RecordAudioAction) -> Unit,

) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(72.dp, androidx.compose.ui.Alignment.CenterHorizontally),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

    ) {
        IconButton(
            onClick = {
                onAction(RecordAudioAction.CancelRecording) },

            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            ),
            modifier = Modifier.size(48.dp)
        ) {
            Icon(imageVector = Icons.Default.Close,
                contentDescription = "Cancel",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        }

        Box(modifier = Modifier
            .size(72.dp)
            .background(
                color = primary50,
                shape = CircleShape
            )
            ,
            contentAlignment = androidx.compose.ui.Alignment.Center
        ){
            IconButton(
                onClick = {
                    onAction(RecordAudioAction.StartRecording)

                  /*  if (hasRecordAudioPermission) {
                        onAction(RecordAudioAction.StartRecording)
                    }else{
                        onAction(RecordAudioAction.OpenDialog)
                    }*/
                }
            ){
                Icon(
                    painter = painterResource(R.drawable.microphone),
                    contentDescription = "Start recording",
                    modifier = Modifier.size(72.dp),
                    tint = Color.White
                )
            }
        }
        IconButton(
            onClick = {
                onAction(RecordAudioAction.SaveRecording)
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            modifier = Modifier.size(48.dp)
        ) {
            Image(imageVector = Icons.Default.Check,
                contentDescription = "Save",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun PermissionDeclinedDialog(
    modifier: Modifier = Modifier,
    isPermanentlyDeclined: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
     Dialog(
        onDismissRequest = {
            onDismiss()
        },
    ) {
        val text = if (isPermanentlyDeclined) {
            "It seems you permanently declined the permission to record audio." +
                    "You can enable it in the app settings."
        } else {
            "It seems you declined the permission to record audio."
        }
        val buttonConfirmText = if (isPermanentlyDeclined) {
            "Open Settings"
        } else {
            "Grant Permission"
        }

        Box(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.large
                )
                .padding(16.dp)
        ) {
            Column {
                Text(text)

                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        androidx.compose.ui.Alignment.End
                    ),
                    verticalAlignment = androidx.compose.ui.Alignment.Bottom
                ) {
                    Button(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }

                    Button(onClick = {
                        onDismiss()
                        onConfirm()
                    }) {
                        Text(
                            text = buttonConfirmText,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }

                }
            }
        }
    }
}
fun Activity.openAppSettings(){
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also (::startActivity)

}