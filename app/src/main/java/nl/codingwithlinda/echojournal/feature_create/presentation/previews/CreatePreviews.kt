package nl.codingwithlinda.echojournal.feature_create.presentation.previews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.util.blankMoods
import nl.codingwithlinda.echojournal.feature_create.presentation.CreateEchoScreen
import nl.codingwithlinda.echojournal.feature_create.presentation.components.SelectMoodBottomSheetContent
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState
import nl.codingwithlinda.echojournal.ui.theme.EchoJournalTheme

@Preview
@Composable
private fun CreateScreenPreview() {
    EchoJournalTheme {
        CreateEchoScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
            ,
            uiState = CreateEchoUiState()
                .copy(
                    title = "title",
                    topics = listOf("topic1", "topic2"),
                    isSelectMoodExpanded = true
                ),
            onAction = {},

        )
    }
}