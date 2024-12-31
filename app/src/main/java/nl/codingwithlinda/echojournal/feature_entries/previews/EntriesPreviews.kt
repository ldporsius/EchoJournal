package nl.codingwithlinda.echojournal.feature_entries.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_entries.components.EmptyListComponent
import nl.codingwithlinda.echojournal.feature_entries.components.EntriesTopBar
import nl.codingwithlinda.echojournal.feature_entries.components.SelectMoodComponent
import nl.codingwithlinda.echojournal.feature_entries.components.SelectMoodItem
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

@Preview(showBackground = true)
@Composable
private fun SelectMoodItemPreview() {
    EchoJournalTheme {
        SelectMoodItem(
            modifier = Modifier,
            icon = {
                Icon(
                    imageVector =  ImageVector.vectorResource(id = R.drawable.mood_sad),
                    contentDescription = null,
                )
            },
            text = {
                Text("Sad")
            },
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectMoodItemPreview2() {
    EchoJournalTheme {
        SelectMoodItem(
            modifier = Modifier,
            icon = {
                Icon(
                    painter =  painterResource(id = R.drawable.mood_sad),
                    contentDescription = null,
                )
            },
            text = {
                Text("Sad")
            },
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectMoodPreview() {

    EchoJournalTheme {
        SelectMoodComponent(
            modifier = Modifier.background(color = Color.White),
            onMoodSelected = {}
        )
    }
}