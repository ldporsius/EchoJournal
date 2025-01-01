package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.compose.runtime.Composable
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosScreen
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

@Composable
fun EchosRoot() {

   EchosScreen(
      entries = listOf("Entry 1", "Entry 2", "Entry 3"),
      topics = listOf(
         UiTopic(
            name = "Topic 1",
            isSelected = true
         ),
         UiTopic(
            name = "Topic 2",
            isSelected = false
         ),
   )
   )
}