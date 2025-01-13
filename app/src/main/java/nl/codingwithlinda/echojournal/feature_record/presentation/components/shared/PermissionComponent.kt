package nl.codingwithlinda.echojournal.feature_record.presentation.components.shared

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

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