package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.components.EchoPlaybackComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.AddTopicComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.CreateCancelSaveButtons
import nl.codingwithlinda.echojournal.feature_create.presentation.components.ExistingTopicsComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.SelectMoodBottomSheetContent
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState
import nl.codingwithlinda.echojournal.feature_create.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.PlaybackIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEchoScreen(
    modifier: Modifier = Modifier,
    uiState: CreateEchoUiState,
    topicsUiState: TopicsUiState,
    selectedTopics: List<String>,
    onAction: (CreateEchoAction) -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
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

        //val moodColor = uiState.selectedMood?.let { Color(it.color) } ?: Color.Gray
        EchoPlaybackComponent(
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
        AnimatedContent(topicsUiState.isExpanded, label = "") { expanded ->
            when(expanded){
                true -> {
                    AddTopicComponent(
                        topic = topicsUiState.searchText,
                        topics = topicsUiState.topics,
                        shouldShowCreate = topicsUiState.shouldShowCreate(),
                        onAction = onAction
                    )
                }
                false -> {
                    ExistingTopicsComponent(
                        selectedTopics = selectedTopics,
                        onAction = onAction
                    )
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