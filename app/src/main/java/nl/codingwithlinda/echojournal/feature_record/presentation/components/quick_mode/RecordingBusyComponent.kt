package nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.ui.theme.primary50

@Composable
fun RecordingBusyComponent(
    modifier: Modifier = Modifier,
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale1 = infiniteTransition. animateFloat(
        1f,     1.5f,
        infiniteRepeatable(tween(600), RepeatMode. Reverse), label = ""
    )
    Box(modifier = modifier

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
            color = primary50,
            shape = CircleShape
        )
        ,
        contentAlignment = androidx.compose.ui.Alignment.Center
    ){
        IconButton(
            onClick = {
                //
            }
        ){
            Icon(
                painter = painterResource(R.drawable.microphone),
                contentDescription = "Confirm",
                modifier = Modifier.size(72.dp),
                tint = Color.White
            )
        }
    }
}