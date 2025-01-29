package nl.codingwithlinda.echojournal.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction
import nl.codingwithlinda.echojournal.core.presentation.topics.AddTopicComponent
import nl.codingwithlinda.echojournal.core.presentation.topics.ExistingTopicsComponent
import nl.codingwithlinda.echojournal.core.presentation.topics.TopicInputComponent
import nl.codingwithlinda.echojournal.core.presentation.topics.topicModifier
import nl.codingwithlinda.echojournal.feature_create.presentation.components.MoodItemVertical
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_settings.presentation.state.SettingsAction
import nl.codingwithlinda.echojournal.ui.theme.gray6
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    moods: List<UiMood>,
    topics: List<Topic>,
    topicInput: String,
    selectedTopics: List<Topic>,
    shouldShowTopicList: Boolean,
    shouldShowCreateTopic: Boolean,
    onAction: (SettingsAction) -> Unit,
    onTopicAction: (TopicAction) -> Unit
) {
    var existingTopicsPosition by remember {
        mutableStateOf(Offset.Zero)
    }
    var existingTopicsSize by remember {
        mutableStateOf(Size.Zero)
    }

    val nestedScrollConnection = rememberNestedScrollInteropConnection()

    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = modifier
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .nestedScroll(nestedScrollConnection)
                    .padding(16.dp)
            ) {


                OutlinedCard(
                    modifier = Modifier
                        .padding(2.dp)
                        .padding(16.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "My mood",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            "Select default mood to apply to all new entries",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            moods.forEach { mood ->
                                MoodItemVertical(
                                    modifier = Modifier
                                        .clickable {
                                            onAction(SettingsAction.SelectMoodAction(mood.mood))
                                        },
                                    mood
                                )
                            }
                        }
                    }
                }


                OutlinedCard(
                    modifier = Modifier
                        .padding(2.dp)
                        .padding(16.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "My topics",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            "Select default topics to apply to all new entries",
                            style = MaterialTheme.typography.labelMedium
                        )

                        Box(
                            Modifier
                                .fillMaxWidth()
                        ) {

                            ExistingTopicsComponent(
                                modifier = Modifier
                                    .onGloballyPositioned {
                                        existingTopicsPosition = it.positionInRoot()
                                    }
                                    .onSizeChanged {
                                        existingTopicsSize = it.toSize()
                                    },
                                selectedTopics = selectedTopics,
                                addTopicComponent = {
                                    if (shouldShowTopicList){
                                        TopicInputComponent(
                                            modifier = Modifier
                                                .sizeIn(minWidth = 27.dp, maxWidth = 200.dp)
                                                .wrapContentSize()
                                                .background(gray6, CircleShape)
                                            ,
                                            topicText = topicInput,
                                            onTopicAction = onTopicAction
                                        )
                                    }
                                    else {
                                        IconButton(
                                            onClick = {
                                                onTopicAction(TopicAction.ShowHideTopics(true))
                                            },
                                            modifier = Modifier.height(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = null
                                            )
                                        }

                                    }
                                }
                            ) {
                                onTopicAction(it)
                            }


                        }
                    }
                }

            }



            if (shouldShowTopicList) {
                Box(
                    modifier = Modifier
                        .offset {
                            val y = existingTopicsPosition.y + existingTopicsSize.height + 8.dp.toPx()
                            println("Existing topic position: ${existingTopicsPosition}, y: $y")
                            println("Existing topic height: ${existingTopicsSize.height}, y: $y")
                            IntOffset(x = 0, y = y.roundToInt())
                        }
                        .height(300.dp)
                        .background(color = Color.White)
                ) {
                    AddTopicComponent(
                       modifier = Modifier,
                        topic = topicInput,
                        topics = topics,
                        shouldShowCreate = shouldShowCreateTopic,
                        onAction = onTopicAction
                    )
                }
            }
        }
    }




}