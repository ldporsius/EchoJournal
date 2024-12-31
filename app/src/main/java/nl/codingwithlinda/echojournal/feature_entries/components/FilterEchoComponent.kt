package nl.codingwithlinda.echojournal.feature_entries.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun FilterEchoComponent(
    modifier: Modifier = Modifier,
    selectedMoods: String,
    selectedTopics: String,
    onFilterSelected: (String) -> Unit
) {

    var showSelectMoods by remember {
        mutableStateOf(false)
    }
    Column {
        Row(
            modifier = modifier
        ) {
            FilterChip(
                selected = true,
                onClick = {showSelectMoods = true },
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

        if (showSelectMoods) {
            SelectMoodComponent(
                onMoodSelected = {

                    showSelectMoods = false
                }
            )
        }

    }
}