package nl.codingwithlinda.echojournal.core.presentation.topics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction

@Composable
fun AddTopicComponentTextButton(
    modifier: Modifier = Modifier,
    onTopicAction: (TopicAction) -> Unit
) {
    TextButton(
        onClick = {
            onTopicAction(TopicAction.ShowHideTopics(true))
        },
        modifier =
        Modifier
            .heightIn(32.dp, 32.dp)
            .background(color = Color.Transparent, shape = CircleShape)


    ) {
        val txt = "Add topic"
        Text("# $txt"
            , style = MaterialTheme.typography.labelSmall)
    }
}