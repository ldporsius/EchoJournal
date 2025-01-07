package nl.codingwithlinda.echojournal.feature_create.presentation.previews

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.components.GradientButton
import nl.codingwithlinda.echojournal.feature_create.presentation.CreateEchoScreen
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoUiState
import nl.codingwithlinda.echojournal.feature_create.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.ui.theme.EchoJournalTheme
import nl.codingwithlinda.echojournal.ui.theme.buttonDisabledGradient
import nl.codingwithlinda.echojournal.ui.theme.buttonGradient

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

                    isSelectMoodExpanded = true
                ),
            topicsUiState = TopicsUiState(),
            selectedTopics = listOf(),
            onAction = {},
            onCancel = {}

        )
    }
}

@Preview
@Composable
private fun GradientButtonPreview() {
    GradientButton(
        text = {
            Row {
                Icon(imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .padding(end = 8.dp)
                )
                Text("Confirm")
            }
        },
        gradient = buttonDisabledGradient,
        onClick = {

        },
        enabled = true,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}