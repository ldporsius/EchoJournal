package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.components.EchoPlaybackComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.AddTopicComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.CreateCancelSaveButtons
import nl.codingwithlinda.echojournal.feature_create.presentation.components.SelectMoodBottomSheetContent
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState
import nl.codingwithlinda.echojournal.feature_create.presentation.state.TopicsUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateEchoScreen(
    modifier: Modifier = Modifier,
    uiState: CreateEchoUiState = CreateEchoUiState(),
    topicsUiState: TopicsUiState,
    selectedTopics: List<String>,
    onAction: (CreateEchoAction) -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onAction(CreateEchoAction.ShowHideMoods(true))
                }
            ) {
                uiState.SelectedMoodIcon()
            }
            OutlinedTextField(
                value = uiState.title,
                onValueChange = {
                    onAction(CreateEchoAction.TitleChanged(it))
                },
                placeholder = {
                    Text("Add Title ...",
                        style = MaterialTheme.typography.titleLarge

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
            onAction = {
                onAction(CreateEchoAction.PlayEcho)
            },
            moodColor = androidx.compose.ui.graphics.Color.Gray,
            duration = "1:23",
            amplitudes = listOf(),
            echoId = ""
        )

        //selected topics
        AnimatedContent(topicsUiState.isExpanded, label = "") { expanded ->
            when(expanded){
                true -> {
                    AddTopicComponent(
                        topic = topicsUiState.searchText,
                        topics = topicsUiState.topics,
                        isTopicsExpanded = topicsUiState.isExpanded,
                        shouldShowCreate = topicsUiState.shouldShowCreate(),
                        onAction = onAction
                    )
                }
                false -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        TextButton(
                            onClick = {
                                onAction(CreateEchoAction.ShowHideTopics(true))
                            }
                        ) {
                            val txt = if (selectedTopics.isEmpty()) "Topic" else ""
                            Text("# $txt")
                        }
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            selectedTopics.forEach {
                                Row(
                                    modifier = Modifier
                                        .background(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = CircleShape
                                    ).heightIn(32.dp, 40.dp)
                                        .padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "# $it",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontSize = TextUnit(12f, TextUnitType.Sp),
                                        modifier = Modifier
                                    )
                                    IconButton(
                                        onClick = {
                                            onAction(CreateEchoAction.RemoveTopic(it))
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
                        }
                    }
                }
            }
        }


        //description
        OutlinedTextField(
            value = uiState.description,
            onValueChange = {
                onAction(CreateEchoAction.DescriptionChanged(it))
            },
            placeholder = {
                Text("Add Description",
                    style = MaterialTheme.typography.titleSmall

                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
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