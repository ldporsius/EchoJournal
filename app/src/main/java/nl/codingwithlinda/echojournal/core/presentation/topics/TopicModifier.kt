package nl.codingwithlinda.echojournal.core.presentation.topics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.ui.theme.gray6

@Composable
fun Modifier.topicModifier() = Modifier
    .background(
        color = gray6,
        shape = CircleShape
    ).heightIn(32.dp, 32.dp)
    .padding(horizontal = 8.dp)