package nl.codingwithlinda.echojournal.feature_record.presentation.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.ui.theme.buttonGradient

@Composable
fun AddRecordingComponent(
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(64.dp)
            .background(
                brush = buttonGradient,
                shape = CircleShape
            )
        ,
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
    }
}