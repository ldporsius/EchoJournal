package nl.codingwithlinda.echojournal.feature_entries.previews

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.echojournal.feature_entries.components.EmptyListComponent
import nl.codingwithlinda.echojournal.feature_entries.components.EntriesTopBar
import nl.codingwithlinda.echojournal.ui.theme.EchoJournalTheme

@Preview(showBackground = true)
@Composable
private fun EntriesTopBarPreview() {

    EchoJournalTheme {
        EntriesTopBar(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun EmptyListPreview() {

    EchoJournalTheme {
       EmptyListComponent()
    }
}