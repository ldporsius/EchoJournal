package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

@Composable
fun FilterEchoComponent(
    modifier: Modifier = Modifier,
    selectedMoods: String,
    topics: List<UiTopic>,
    selectedTopics: String,
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
                label = { Text(selectedTopics) }
            )
        }

        if (showSelectMoods) {
            SelectMoodComponent(
                onMoodSelected = {
                    showSelectMoods = false
                }
            )
        }

        if (showSelectTopics) {
            SelectTopicComponent(
                topics = topics,
                onTopicSelected = {
                    showSelectTopics = false
                },
                onDismiss = {
                    showSelectTopics = false
                }
            )
        }
    }
}