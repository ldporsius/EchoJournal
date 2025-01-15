package nl.codingwithlinda.echojournal.feature_entries.presentation.previews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import nl.codingwithlinda.echojournal.core.presentation.components.EchoPlaybackComponent
import nl.codingwithlinda.echojournal.core.presentation.mappers.coloredMoods
import nl.codingwithlinda.echojournal.core.presentation.mappers.toUi
import nl.codingwithlinda.echojournal.core.presentation.util.DateTimeFormatterDuration
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchoListItem
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EchoListItemContent
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.EmptyListComponent
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.FilterEchoComponent
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.SelectMoodComponent
import nl.codingwithlinda.echojournal.feature_entries.presentation.components.SelectMoodItem
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.mapping.toUi
import nl.codingwithlinda.echojournal.ui.theme.EchoJournalTheme

@Preview(showBackground = true)
@Composable
private fun EntriesFilterBarPreview() {

    EchoJournalTheme {
        FilterEchoComponent (
            modifier = Modifier.fillMaxWidth(),

            moodsUiState = MoodsUiState(
                moods = coloredMoods.entries.sortedBy { it.key }.map { it.value },
                selectedMoods = listOf(Mood.SAD.toUi())
            ),
            topicsUiState = TopicsUiState(
                topics = fakeTopics,
                selectedTopics = listOf(fakeTopics[0]),
                selectedTopicsUiText = UiText.DynamicString(
                    fakeTopics[0].name + ", " + fakeTopics[1].name
                ),
                shouldShowClearSelection = true
            ),
            onAction = {}

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
                moods = coloredMoods.entries.sortedBy { it.key }.map { it.value },
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
           shouldShowVerticalDivider = true,
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
                  uiEcho = fakeEcho(mood = Mood.SAD, timestamp = System.currentTimeMillis()).toUi(
                     timeStamp = "00: 00"
                  ),
                  replayComponent =  {},
                  onFilterTopic = {}
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
            uiEcho = fakeEcho(mood = Mood.SAD, timestamp = System.currentTimeMillis()).toUi(
                "10:12"
                ),
            replayComponent =  {
                EchoPlaybackComponent(
                    modifier = Modifier,
                    playbackIcon = {

                    },
                    duration = DateTimeFormatterDuration.formatDateTimeMillis(1000),
                    amplitudes = fakeAmplitudes(),
                    amplitudeColor = {
                        Color.Red
                    },
                )
            },
            onFilterTopic = {}

        )
    }
}