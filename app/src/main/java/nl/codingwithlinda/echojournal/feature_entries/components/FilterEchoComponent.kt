package nl.codingwithlinda.echojournal.feature_entries.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FilterEchoComponent(
    modifier: Modifier = Modifier,
    selectedMoods: String,
    selectedTopics: String,
    onFilterSelected: (String) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        FilterChip(
            selected = true,
            onClick = { },
            label = {
                Text(selectedMoods)
            }
        )
        FilterChip(
            selected = true,
            onClick = { },
            label = { Text(selectedTopics) }
        )
    }
}