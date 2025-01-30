package nl.codingwithlinda.echojournal.core.presentation.topics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction
import nl.codingwithlinda.echojournal.ui.theme.gray6
import nl.codingwithlinda.echojournal.ui.theme.outlineVariant

@Composable
fun AddTopicComponentTextButton(
    modifier: Modifier = Modifier,
    onTopicAction: (TopicAction) -> Unit
) {
    TextButton(
        onClick = {
            onTopicAction(TopicAction.ShowHideTopics(true))
        },
        modifier = modifier
            .heightIn(32.dp, 32.dp)
            .background(color = Color.Transparent, shape = CircleShape),
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = outlineVariant,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = outlineVariant
        )


    ) {
        val txt = "Topic"
        Text("# $txt"
            , style = MaterialTheme.typography.bodySmall)
    }
}