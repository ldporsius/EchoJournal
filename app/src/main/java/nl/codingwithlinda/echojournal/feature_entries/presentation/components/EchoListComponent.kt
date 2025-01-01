package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

@Composable
fun EchoListComponent(
    entries: List<String>,
    topics: List<UiTopic>,
    selectedMoods: String,
    selectedTopics: String
) {
    Column {

        FilterEchoComponent(
            modifier = Modifier.padding(start = 16.dp),
            topics = topics,
            selectedMoods = selectedMoods,
            selectedTopics = selectedTopics,

        )
        LazyColumn {
            items(entries) {
                EchoListItem(
                    icon = {
                        Text(it)
                    },
                    content = {
                        Text(it)
                    }
                )
            }
        }
    }
}