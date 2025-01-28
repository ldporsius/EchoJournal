package nl.codingwithlinda.echojournal.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toSize
import nl.codingwithlinda.echojournal.core.domain.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.state.TopicAction
import nl.codingwithlinda.echojournal.feature_create.presentation.components.AddTopicComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.ExistingTopicsComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.MoodItemVertical
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeTopics
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_settings.presentation.state.SettingsAction
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    moods: List<UiMood>,
    topic: Topic?,
    onAction: (SettingsAction) -> Unit,
    onTopicAction: (TopicAction) -> Unit
) {
    var existingTopicsPosition by remember {
        mutableStateOf(Offset.Zero)
    }
    var existingTopicsSize by remember {
        mutableStateOf(Size.Zero)
    }
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
    Box(
        Modifier.fillMaxSize()
    ) {

            Column(
                modifier = modifier
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
                                        }
                                    ,
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
                                "Select default topic to apply to all new entries",
                                style = MaterialTheme.typography.labelMedium
                            )

                            Box(
                                Modifier
                                    .fillMaxWidth()
                            ) {
                                ExistingTopicsComponent(
                                    modifier = Modifier
                                        .onSizeChanged {
                                            existingTopicsSize = it.toSize()
                                        }
                                        .onGloballyPositioned {
                                            existingTopicsPosition = it.positionInParent()
                                        },
                                    selectedTopics = listOf()
                                ) { }



                                AddTopicComponent(
                                    modifier = Modifier
                                        .background(color = Color.White)
                                        .offset {
                                            val y = existingTopicsPosition.y
                                            println("Existing topic position: ${existingTopicsPosition}, y: $y")
                                            println("Existing topic height: ${existingTopicsSize.height}, y: $y")
                                            IntOffset(x = 0, y = y.roundToInt())
                                        },
                                    topic = topic?.name ?: "",
                                    topics = fakeTopics,
                                    shouldShowCreate = false,
                                    onAction = onTopicAction
                                )
                            }
                        }
                    }

                }
            }
        }



}