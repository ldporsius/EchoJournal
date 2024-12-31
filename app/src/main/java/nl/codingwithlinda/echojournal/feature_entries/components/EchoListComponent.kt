package nl.codingwithlinda.echojournal.feature_entries.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EchoListComponent(
    entries: List<String>,
    selectedMoods: String,
    selectedTopics: String
) {
    Column {

        FilterEchoComponent(
            modifier = Modifier,
            selectedMoods = selectedMoods,
            selectedTopics = selectedTopics,
            onFilterSelected = {}
        )
        LazyColumn {
            items(entries) {
                Text(text = it)
            }
        }
    }
}