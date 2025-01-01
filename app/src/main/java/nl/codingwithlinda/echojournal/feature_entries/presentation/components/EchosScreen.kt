package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

@Composable
fun EchosScreen(
    entries: List<UiEcho>,
    topics: List<UiTopic>
) {

    Scaffold(
        modifier = Modifier.safeContentPadding(),
        topBar = { EchosTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            if (entries.isEmpty()) {
               EmptyListComponent(
                   Modifier.fillMaxSize()
               )
            }
            else{
                EchoListComponent(
                    entries = entries,
                    topics = topics,
                    selectedMoods = "All Moods",
                    selectedTopics = topics.filter { it.isSelected }.map { it.name }.joinToString()
                )
            }
        }
    }
}