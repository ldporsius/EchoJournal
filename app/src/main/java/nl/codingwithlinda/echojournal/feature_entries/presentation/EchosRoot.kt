package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.compose.runtime.Composable
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosScreen
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeGroups
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiTopics

@Composable
fun EchosRoot() {

   EchosScreen(
      entries = fakeGroups,
      topics = fakeUiTopics()
   )
}