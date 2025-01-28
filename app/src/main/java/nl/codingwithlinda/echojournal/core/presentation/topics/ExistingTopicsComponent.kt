package nl.codingwithlinda.echojournal.core.presentation.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExistingTopicsComponent(
    modifier: Modifier = Modifier,
    selectedTopics: List<Topic>,
    addTopicComponent: @Composable () -> Unit,
    onAction: (TopicAction) -> Unit
) {

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
        ,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        selectedTopics.forEach {
            TopicItem(
                modifier =Modifier
                    .topicModifier()
                    .alignByBaseline()
                ,
                topic = it,
                onRemove = {
                    onAction(TopicAction.RemoveTopic(it))
                }
            )
        }

        addTopicComponent()

    }
}