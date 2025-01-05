package nl.codingwithlinda.echojournal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho

@Composable
fun EchoPlaybackComponent(
    modifier: Modifier = Modifier,
    uiEcho: UiEcho,
    onAction: (ReplayEchoAction) -> Unit
) {

    val iconTint = Color(uiEcho.mood.color)
    val amplitudes = uiEcho.amplitudes
    val duration = uiEcho.duration
    var playIconSize by remember {
        mutableStateOf(Size.Zero)
    }
    val amplitudeWidth = 6.dp
    val amplitudeSpacing = 1.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = iconTint.copy(.25f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(100)
            )
            .padding(start = 6.dp, end = 16.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

    ){
        IconButton(onClick = {
            onAction(ReplayEchoAction.Play(uiEcho.id))
        },
            modifier = Modifier
                .onSizeChanged {
                    playIconSize = it.toSize()
                },
            colors = androidx.compose.material3.IconButtonDefaults.iconButtonColors(
                containerColor = androidx.compose.ui.graphics.Color.White,
                contentColor = iconTint
            )
        ) {
            Icon(imageVector = androidx.compose.material.icons.Icons.Default.PlayArrow, contentDescription = null)
        }

        Box(
            Modifier
                .weight(1f)
                .height(48.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(1))
                .drawBehind {
                    val width = size.width
                    val height = size.height

                    (0 until amplitudes.size).forEach { index ->
                        val x = index * (amplitudeWidth.toPx() + amplitudeSpacing.toPx())
                        val y = height / 2

                        val amplitudeHeight = height * amplitudes[index]

                        val offsetStart = Offset(x = x, y = y - amplitudeHeight / 2)
                        val offsetEnd = Offset(x = x, y = y + amplitudeHeight / 2)

                        drawLine(
                            color = iconTint,
                            start = offsetStart,
                            end = offsetEnd,
                            strokeWidth = amplitudeWidth.toPx()
                        )
                    }
                }
        )
        Spacer(modifier = Modifier
            .padding(start = 1.dp)
            .height(48.dp))
        Text(
            text = duration,
            modifier = Modifier
                .padding(start = 8.dp)
            ,
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall
        )
    }
}