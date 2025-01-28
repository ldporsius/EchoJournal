package nl.codingwithlinda.echojournal.feature_settings.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.echojournal.core.presentation.mappers.coloredMoods
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeTopics
import nl.codingwithlinda.echojournal.feature_settings.presentation.SettingsScreen
import nl.codingwithlinda.echojournal.ui.theme.EchoJournalTheme

@Preview
@Composable
private fun SettingsScreenPreview() {
    EchoJournalTheme { 
        SettingsScreen(
            modifier = Modifier,
            moods = coloredMoods.values.toList(),
            topics = fakeTopics,
            topicInput = "todo",
            selectedTopics = fakeTopics.take(1),
            shouldShowTopicList = false,
            shouldShowCreateTopic = true,
            onAction = {},
            onTopicAction = {}
        )
    }
}