package nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

