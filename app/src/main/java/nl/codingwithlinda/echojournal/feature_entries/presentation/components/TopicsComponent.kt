package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.domain.model.Topic

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopicsComponent(
    tags: List<Topic>,
    onClick: (Topic) -> Unit,
    modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        tags.forEach {
            Row (modifier = Modifier
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(100))
                .background(color = Color.LightGray)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .clickable {
                    onClick(it)
                },
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            ) {
                Text("#")
                Text(it.name)
            }
        }
    }
}