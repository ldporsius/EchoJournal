package nl.codingwithlinda.echojournal.feature_entries.presentation

import androidx.compose.runtime.Composable
import nl.codingwithlinda.echojournal.feature_entries.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.domain.util.GroupByTimestamp
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosScreen
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.previews.fakeUiTopics
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEchoGroup

@Composable
fun EchosRoot() {

   val timestamp = System.currentTimeMillis()
   val yesterday = timestamp - 86400000
   val entries =  listOf(
      fakeEcho(Mood.STRESSED, timestamp = timestamp),
      fakeEcho(Mood.SAD, timestamp = yesterday),
      fakeEcho(Mood.NEUTRAL, timestamp = yesterday),
      fakeEcho(Mood.PEACEFUL, timestamp = yesterday),
      fakeEcho(Mood.EXITED, timestamp = yesterday),
   )
   val groups = GroupByTimestamp.groupByTimestamp(entries).map {
      UiEchoGroup(
         header = it.key,
         entries = it.value.map {
            fakeUiEcho(it.mood, it.timeStamp)
         }
      )
   }
   EchosScreen(
      entries = groups,
   topics = fakeUiTopics()
   )
}