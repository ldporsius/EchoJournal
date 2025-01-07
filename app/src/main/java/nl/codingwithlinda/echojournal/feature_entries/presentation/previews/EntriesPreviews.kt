package nl.codingwithlinda.echojournal.feature_entries.presentation.previews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.domain.model.Mood
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchoListItem
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchoListItemContent
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EmptyListComponent
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchosTopBar
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.SelectMoodComponent
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.SelectMoodItem
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.mapping.toUi
import nl.codingwithlinda.echojournal.feature_entries.presentation.util.moodToColorMap
import nl.codingwithlinda.echojournal.ui.theme.EchoJournalTheme

@Preview(showBackground = true)
@Composable
private fun EntriesTopBarPreview() {

    EchoJournalTheme {
        EchosTopBar(
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
                    tint = Color.Unspecified
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
                Image(
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
            moodsUiState = MoodsUiState(
                moods = moodToColorMap.entries.sortedBy { it.key }.map { it.value },
                selectedMoods = emptyList()
            ),
            onMoodSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EchoListItemPreview() {

    EchoJournalTheme {
       EchoListItem(
            modifier = Modifier
                .background(color = Color.LightGray),
           icon = {
               Image(painter = painterResource(id = R.drawable.mood_sad),
                   contentDescription = null,
                   contentScale = ContentScale.Inside
               )
           },
           content = {
              EchoListItemContent(
                  modifier = Modifier
                      .background(color = Color.White)
                      .fillMaxWidth()
                      .wrapContentHeight()
                      .padding(16.dp)
                  ,
                  uiEcho = fakeUiEcho("0", mood = Mood.SAD, timestamp = "17:59"),
                  replayComponent =  {}
              )
           }
        )
    }
}

@Preview
@Composable
private fun EchoListItemContentPreview() {
    EchoJournalTheme {
        EchoListItemContent(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .wrapContentHeight(),
            uiEcho = fakeUiEcho("1", mood = Mood.SAD, timestamp = "17:59"),
            replayComponent =  {}
        )
    }
}