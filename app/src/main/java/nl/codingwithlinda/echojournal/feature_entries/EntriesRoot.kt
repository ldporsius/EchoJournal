package nl.codingwithlinda.echojournal.feature_entries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EntriesRoot() {

    Scaffold {padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Entries")
        }
    }
}