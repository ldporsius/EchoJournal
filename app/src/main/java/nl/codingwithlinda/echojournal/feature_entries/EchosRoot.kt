package nl.codingwithlinda.echojournal.feature_entries

import androidx.compose.runtime.Composable
import nl.codingwithlinda.echojournal.feature_entries.components.EchosScreen

@Composable
fun EchosRoot() {

   EchosScreen(
      entries = listOf("Entry 1", "Entry 2", "Entry 3")
   )
}