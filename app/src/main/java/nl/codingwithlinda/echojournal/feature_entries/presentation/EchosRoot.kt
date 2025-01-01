package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.compose.runtime.Composable
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosScreen
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiTopics
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

@Composable
fun EchosRoot() {

   EchosScreen(
      entries = listOf(
         fakeUiEcho(),
         fakeUiEcho(),
         fakeUiEcho(),
         fakeUiEcho(),
      ),
   topics = fakeUiTopics()
   )
}