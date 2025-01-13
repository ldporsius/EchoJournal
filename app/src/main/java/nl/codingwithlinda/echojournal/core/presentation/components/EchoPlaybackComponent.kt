package nl.codingwithlinda.echojournal.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp

@Composable
fun EchoPlaybackComponent(
    modifier: Modifier,
    playbackIcon: @Composable () -> Unit,
    duration: String,
    amplitudes: List<Float>,
    amplitudeColor: (index: Int) -> Color,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)

            .padding(start = 0.dp, end = 16.dp)
        ,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

    ){

        playbackIcon()

        Box(
            Modifier
                .weight(1f)
                .height(48.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(1))
                .drawBehind {
                    val width = size.width
                    val height = size.height

                    val amplWidth = 4.dp.toPx()

                    val spaceFactor = 0.5f

                    val scaleX = width / (amplitudes.size * (1 + spaceFactor) * amplWidth)

                    this.scale(
                        scaleX = scaleX*0.97f,
                        scaleY = 0.9f,
                        pivot = Offset(0f, center.y)
                    ) {


                        amplitudes.forEachIndexed { index, amplitude ->
                            val color = amplitudeColor(index)
                            val x = index * amplWidth * (1 + spaceFactor)

                            val yTopStart = center.y - (height / 2) * amplitude

                            drawRoundRect(
                                color = color,
                                topLeft = Offset(x, yTopStart),
                                size = Size(
                                    width = amplWidth,
                                    height = (center.y - yTopStart) * 2f
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