package nl.codingwithlinda.echojournal.feature_entries

import androidx.compose.runtime.Composable
import nl.codingwithlinda.echojournal.feature_entries.components.EntriesScreen

@Composable
fun EntriesRoot() {

   EntriesScreen(
      entries = listOf("Entry 1", "Entry 2", "Entry 3")
   )
}