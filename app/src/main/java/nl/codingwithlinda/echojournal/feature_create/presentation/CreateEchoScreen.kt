package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.core.model.Topic
import nl.codingwithlinda.echojournal.core.presentation.components.EchoPlaybackComponent
import nl.codingwithlinda.echojournal.core.presentation.topics.AddTopicComponentTextButton
import nl.codingwithlinda.echojournal.core.presentation.topics.state.TopicAction
import nl.codingwithlinda.echojournal.core.presentation.topics.AddTopicsComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.CreateCancelSaveButtons
import nl.codingwithlinda.echojournal.core.presentation.topics.ExistingTopicsComponent
import nl.codingwithlinda.echojournal.core.presentation.topics.TopicInputComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.SelectMoodBottomSheetContent
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState
import nl.codingwithlinda.echojournal.feature_create.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.ui.theme.gray6
import nl.codingwithlinda.echojournal.ui.theme.outlineVariant
import nl.codingwithlinda.echojournal.ui.theme.secondary70
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEchoScreen(
    modifier: Modifier = Modifier,
    uiState: CreateEchoUiState,
    topicsUiState: TopicsUiState,
    selectedTopics: List<Topic>,
    onAction: (CreateEchoAction) -> Unit,
    onTopicAction: (TopicAction) -> Unit,
    onCancel: () -> Unit
) {
    var existingTopicsPosition by remember {
        mutableStateOf(Rect(Offset.Zero, Size.Zero))
    }
    var existingTopicsSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val nestedScrollConnection = rememberNestedScrollInteropConnection()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onTopicAction(TopicAction.ShowHideTopics(false))
            }
        ,
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = modifier
                .imePadding()
                .fillMaxSize()
                .padding(16.dp)
                //.nestedScroll(nestedScrollConnection)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onAction(CreateEchoAction.ShowHideMoods(true))
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                ) {
                    uiState.SelectedMoodIcon()
                }
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = {
                        onAction(CreateEchoAction.TitleChanged(it))
                    },
                    placeholder = {
                        Text(
                            "Add Title ...",
                            style = MaterialTheme.typography.titleLarge,
                            color = secondary70
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            EchoPlaybackComponent(
                modifier = Modifier
                    .background(
                        color = uiState.playbackBackgroundColor(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(100)
                    ),
                playbackIcon = {
                    uiState.PlaybackIcon(
                        modifier = Modifier,
                        onClick = {
                            onAction(CreateEchoAction.PlayEcho)
                        }
                    )
                },
                duration = uiState.duration,
                amplitudes = uiState.amplitudes,
                amplitudeColor = {
                    uiState.amplitudeColor(it)
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            //selected topics
            ExistingTopicsComponent(
                modifier = Modifier

                ,
                selectedTopics = selectedTopics,
                addTopicComponent = {
                    TopicInputComponent(
                        modifier = Modifier
                            .onGloballyPositioned {
                                existingTopicsPosition =  it.boundsInRoot()
                            }
                            .onSizeChanged {
                                existingTopicsSize = it
                            }
                            .height(48.dp)
                            .background(color = gray6, shape = CircleShape)
                        ,
                        topicText = topicsUiState.searchText,
                        onTopicAction = {
                            onTopicAction(it)
                        }
                    )
                },
                onAction = onTopicAction
            )


            //description
            OutlinedTextField(
                value = uiState.description,
                onValueChange = {
                    onAction(CreateEchoAction.DescriptionChanged(it))
                },
                placeholder = {
                    Text(
                        "Add Description",
                        style = MaterialTheme.typography.bodySmall,
                        color = outlineVariant

                    )
                },
                textStyle = MaterialTheme.typography.bodySmall,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Edit,
                        contentDescription = null)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedTextColor = outlineVariant,
                    unfocusedLeadingIconColor = outlineVariant,
                    focusedBorderColor = Color.Transparent,
                    focusedLeadingIconColor = outlineVariant
                )
            )

            Spacer(modifier = Modifier.weight(1f))
            CreateCancelSaveButtons(
                canSave = uiState.canSave(),
                save = {
                    onAction(CreateEchoAction.Save)
                },
                cancel = {
                    onCancel()
                }
            )
        }

        if (topicsUiState.isExpanded) {
            val y = existingTopicsPosition.top
            println("Existing topic position: ${existingTopicsPosition}, ExistingTopicHeight: ${existingTopicsSize.height}, y: $y")

            Box(
                modifier = Modifier
                    .offset{density
                        IntOffset(0, (y.roundToInt()))
                    }
                    .height(300.dp)
                    .background(Color.White)
                ,
            ) {
                AddTopicsComponent(
                    Modifier,
                    topic = topicsUiState.searchText,
                    topics = topicsUiState.topics,
                    shouldShowCreate = topicsUiState.shouldShowCreate(),
                    onAction = onTopicAction
                )
            }
        }
    }

    val sheetState = rememberModalBottomSheetState()

    if (uiState.isSelectMoodExpanded) {
        ModalBottomSheet(
            onDismissRequest = {
                onAction(CreateEchoAction.ShowHideMoods(false))
            },
            sheetState = sheetState,
        ) {
            SelectMoodBottomSheetContent(
                moods = uiState.moods,
                selectedMood = uiState.selectedMood,
                onAction = onAction
            )
        }
    }

}