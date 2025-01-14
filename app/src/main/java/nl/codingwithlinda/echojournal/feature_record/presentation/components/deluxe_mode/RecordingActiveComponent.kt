package nl.codingwithlinda.echojournal.feature_record.presentation.components.deluxe_mode

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.CancelRecordingButton
import nl.codingwithlinda.echojournal.feature_record.presentation.state.RecordAudioAction
import nl.codingwithlinda.echojournal.ui.theme.buttonGradient
import nl.codingwithlinda.echojournal.ui.theme.primary50

@Composable
fun RecordingActiveComponent(
    modifier: Modifier = Modifier,
    onAction: (RecordAudioAction) -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale1 = infiniteTransition. animateFloat(
        1f,     1.5f,
        infiniteRepeatable(tween(600), RepeatMode. Reverse), label = ""
    )
    LaunchedEffect(true) {
        onAction(RecordAudioAction.StartRecording)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(72.dp, androidx.compose.ui.Alignment.CenterHorizontally),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

    ) {
       CancelRecordingButton(
           modifier = Modifier.size(48.dp),
           onAction = {
               onAction(RecordAudioAction.CancelRecording)
           }
       )
        Box(modifier = Modifier
            .size(72.dp)
            .drawBehind {
                drawCircle(
                    color = primary50.copy(0.15f),
                    radius = 40.dp.toPx() * scale1.value
                )
                drawCircle(
                    color = primary50.copy(0.05f),
                    radius = 48.dp.toPx() * scale1.value
                )
            }.background(
                brush = buttonGradient,
                shape = CircleShape
            )
            ,
            contentAlignment = androidx.compose.ui.Alignment.Center
        ){
            IconButton(
               onClick = {
                   onAction(RecordAudioAction.SaveRecording)
               }
            ){
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Confirm",
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        IconButton(
            onClick = {
                onAction(RecordAudioAction.PauseRecording)
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier.size(48.dp)
        ) {
            Image(painter= painterResource( R.drawable.pause),
                contentDescription = "Pause",
                modifier = Modifier.size(48.dp).padding(8.dp),
                contentScale = ContentScale.FillBounds
            )
        }

    }
}