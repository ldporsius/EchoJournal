package nl.codingwithlinda.echojournal.core.presentation.topics

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.domain.model.Topic

@Composable
fun TopicItem(
    modifier: Modifier = Modifier,
    topic: Topic,
    onRemove: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "# ${topic.name}",
            style = MaterialTheme.typography.titleSmall,
            fontSize = TextUnit(12f, TextUnitType.Sp),
            modifier = Modifier
        )
        IconButton(
            onClick = {
                onRemove()
            },
            modifier = Modifier.size(18.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        }
    }
}