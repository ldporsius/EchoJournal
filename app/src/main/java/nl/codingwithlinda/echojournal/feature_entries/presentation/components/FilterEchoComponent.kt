package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.FilterEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState

@Composable
fun FilterEchoComponent(
    modifier: Modifier = Modifier,
    selectedMoods: String,
    topicsUiState: TopicsUiState,
    onAction: (FilterEchoAction) -> Unit
) {

    var showSelectMoods by remember {
        mutableStateOf(false)
    }
    var showSelectTopics by remember {
        mutableStateOf(false)
    }

    Column {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterChip(
                selected = true,
                onClick = {
                    showSelectTopics = false
                    showSelectMoods = true
                },
                label = {
                    Text(selectedMoods)
                }
            )
            FilterChip(
                selected = true,
                onClick = {
                    showSelectMoods = false
                    showSelectTopics = true
                },
                label = {
                    Text(topicsUiState.selectedTopics.asString())
                },
                trailingIcon = {
                    if (topicsUiState.shouldShowClearSelection) {
                        IconButton(
                            onClick = {
                                onAction(FilterEchoAction.ClearTopicSelection)
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                    }
                }
            )
        }

        if (showSelectMoods) {
            SelectMoodComponent(
                onMoodSelected = {

                }
            )
        }

        if (showSelectTopics) {
            SelectTopicComponent(
                topics = topicsUiState.topics,
                onTopicSelected = {
                    onAction(FilterEchoAction.ToggleSelectTopic(it))
                },
                onDismiss = {
                    showSelectTopics = false
                }
            )
        }
    }
}