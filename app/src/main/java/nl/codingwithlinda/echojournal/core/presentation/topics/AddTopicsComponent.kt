package nl.codingwithlinda.echojournal.core.presentation.topics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction.CreateTopic
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction.SelectTopic
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction.ShowHideTopics

@Composable
fun AddTopicComponent(
    modifier: Modifier = Modifier,
    topic: String,
    topics: List<Topic>,
    shouldShowCreate: Boolean,
    onAction: (TopicAction) -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onAction(ShowHideTopics(true))
            }

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = Color.Black,

                        )
                    .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))

            ) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())


                    ) {
                    topics.forEach { topic ->
                        DropdownMenuItem(
                            text = { Text(topic.name) },
                            leadingIcon = {
                                Text("#")
                            },
                            onClick = {
                                onAction(SelectTopic(topic))
                                onAction(ShowHideTopics(false))
                            }
                        )
                    }

                    AnimatedVisibility(shouldShowCreate) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "+ Create '${topic}'",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            onClick = {
                                onAction(ShowHideTopics(false))
                                onAction(CreateTopic(topic))
                            }
                        )
                    }

                }
            }
            }
        }
}