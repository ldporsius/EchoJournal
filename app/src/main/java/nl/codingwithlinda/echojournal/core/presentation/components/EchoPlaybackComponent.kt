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
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction

@Composable
fun EchoPlaybackComponent(
    modifier: Modifier = Modifier,
    onAction: (ReplayEchoAction) -> Unit,
    moodColor: Color,
    duration: String,
    amplitudes: List<Float>,
    uri: String,
) {

    var playIconSize by remember {
        mutableStateOf(Size.Zero)
    }
    //val amplitudeWidth = 1024.dp
    val amplitudeSpacing = 1.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = moodColor.copy(.25f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(100)
            )
            .padding(start = 6.dp, end = 16.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

    ){
        IconButton(onClick = {
            onAction(ReplayEchoAction.Play(uri))
        },
            modifier = Modifier
                .onSizeChanged {
                    playIconSize = it.toSize()
                },
            colors = androidx.compose.material3.IconButtonDefaults.iconButtonColors(
                containerColor = androidx.compose.ui.graphics.Color.White,
                contentColor = moodColor
            )
        ) {
            Icon(imageVector = androidx.compose.material.icons.Icons.Default.PlayArrow, contentDescription = null)
        }

        Box(
            Modifier
                .weight(1f)
                .height(48.dp)
                //.clip(androidx.compose.foundation.shape.RoundedCornerShape(1))
                .drawBehind {
                    val width = size.width
                    val height = size.height
                    val amplitudeBarCount = (width / (2 * amplitudes.size)).toInt()

                    val amplWidth = 3.dp.toPx()

                    this.scale(
                        scaleX = 1f,
                        scaleY = 1f,
                        pivot = Offset(0f, center.y)
                    ) {
                        amplitudes.forEachIndexed { index, amplitude ->
                            val x = index * amplWidth * 2
                            val y = height / 2

                            val yTopStart = center.y - (height / 2) * amplitude

                            drawRoundRect(
                                color = moodColor,
                                topLeft = Offset(x, yTopStart),
                                size = Size(
                                    amplWidth,
                                    (center.y - yTopStart) * 2f
                                ),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(100f)
                            )

                        }
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