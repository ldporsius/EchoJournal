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
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.FilterEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEchoGroup


@Composable
fun EchosScreen(
    entries: List<UiEchoGroup>,
    moodsUiState: MoodsUiState,
    topicsUiState: TopicsUiState,
    onFilterAction: (FilterEchoAction) -> Unit
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
                    selectedMoods = "All Moods",
                    moodsUiState = moodsUiState,
                    topicsUiState = topicsUiState,
                    onFilterAction = onFilterAction
                )
            }
        }
    }
}