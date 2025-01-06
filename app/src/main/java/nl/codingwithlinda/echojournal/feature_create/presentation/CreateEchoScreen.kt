package nl.codingwithlinda.echojournal.feature_create.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import nl.codingwithlinda.echojournal.core.presentation.components.EchoPlaybackComponent
import nl.codingwithlinda.echojournal.feature_create.presentation.components.SelectMoodBottomSheetContent
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEchoScreen(
    modifier: Modifier = Modifier,
    uiState: CreateEchoUiState = CreateEchoUiState(),
    onAction: (CreateEchoAction) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
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
            onAction = {},
            moodColor = androidx.compose.ui.graphics.Color.Gray,
            duration = "1:23",
            amplitudes = listOf(),
            echoId = ""
        )

        //topic
        var dropdownOffset by remember { mutableStateOf(DpOffset.Zero) }
        var itemHeight by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        Box(
            modifier = Modifier
            .fillMaxWidth()
                .clickable {
                    onAction(CreateEchoAction.ShowHideTopics(true))
                }

        ) {

            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .onSizeChanged {
                            with(density) {
                                itemHeight = it.height.toDp()
                            }
                        },
                    value = uiState.topic,
                    onValueChange = {
                        onAction(CreateEchoAction.TopicChanged(it))
                    },
                    placeholder = {
                        Text(
                            "Topic",
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    leadingIcon = {
                        Text("#")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = androidx.compose.ui.text.input.ImeAction.Done
                    ),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                )
                if (uiState.isTopicsExpanded) {
                    Card(
                        modifier = Modifier
                            .zIndex(1000f)
                            .verticalScroll(rememberScrollState())
                           // .windowInsetsPadding(WindowInsets.ime.only(WindowInsetsSides.Bottom)),

                        //offset = dropdownOffset

                    ) {
                        uiState.topics.forEach { topic ->
                            DropdownMenuItem(
                                text = { Text(topic) },
                                onClick = {
                                    onAction(CreateEchoAction.SelectTopic(topic))
                                }
                            )
                        }

                        DropdownMenuItem(
                            text = {
                                Text(
                                    "+ Create '${uiState.topic}'",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            onClick = {
                                onAction(CreateEchoAction.ShowHideTopics(false))
                                onAction(CreateEchoAction.CreateTopic(uiState.topic))
                            }
                        )
                    }
                }
            }
        }


        //description
        OutlinedTextField(
            value = uiState.title,
            onValueChange = {
                onAction(CreateEchoAction.TitleChanged(it))
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
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

        if (uiState.isSelectMoodExpanded) {
            ModalBottomSheet(
                onDismissRequest = {
                    onAction(CreateEchoAction.ShowHideMoods(false))
                },
                sheetState = sheetState,
            ) {
                SelectMoodBottomSheetContent(
                    moods = uiState.moods,
                    onAction = onAction
                )
            }
        }

}