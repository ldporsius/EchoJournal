package nl.codingwithlinda.echojournal.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GradientButton(
    text: @Composable () -> Unit,
    gradient : Brush,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.LightGray,
        ),
        contentPadding = PaddingValues(),
        enabled = enabled,
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
             text()
        }
    }
}