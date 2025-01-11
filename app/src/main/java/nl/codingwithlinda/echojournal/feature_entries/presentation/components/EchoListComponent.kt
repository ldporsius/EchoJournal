package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.components.EchoPlaybackComponent
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.FilterEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEchoGroup


@Composable
fun EchoListComponent(
    entries: List<UiEchoGroup>,
    selectedMoods: @Composable () -> Unit,
    moodsUiState: MoodsUiState,
    topicsUiState: TopicsUiState,
    replayUiState: (uiEcho: UiEcho) -> ReplayUiState,
    onFilterAction: (FilterEchoAction) -> Unit,
    onReplayAction: (ReplayEchoAction) -> Unit
) {
    Column {

        FilterEchoComponent(
            modifier = Modifier.padding(start = 16.dp),
            selectedMoods = selectedMoods,
            moodsUiState = moodsUiState ,
            topicsUiState = topicsUiState,
            onAction = onFilterAction
        )
        LazyColumn(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {

            items(entries) { uiEchoGroup ->
                Text(uiEchoGroup.header.asString().uppercase(),
                    modifier = Modifier.padding(bottom = 16.dp))
                val uiEchos = uiEchoGroup.entries
                for (uiEcho in uiEchos) {
                    EchoListItem(
                        modifier = Modifier,
                        icon = {
                            Image(
                                painter = painterResource(id = uiEcho.mood.icon),
                                contentDescription = null
                            )
                        },
                        content = {
                            val replayUiState1 = replayUiState(uiEcho)
                            EchoListItemContent(
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                uiEcho = uiEcho,
                                replayComponent = {
                                    EchoPlaybackComponent(
                                        modifier = Modifier
                                            .background(
                                                color = replayUiState1.backgroundColor(),
                                                shape = androidx.compose.foundation.shape.RoundedCornerShape(100)
                                            ),
                                        playbackIcon = {
                                            PlaybackIcon(
                                                modifier = Modifier,
                                                id = uiEcho.id,
                                                uri = uiEcho.uri,
                                                moodColor = Color( uiEcho.mood.color),
                                                playbackState = replayUiState1.playbackState,
                                                onAction = onReplayAction
                                            )
                                        },
                                        duration = uiEcho.duration,
                                        amplitudes = uiEcho.amplitudes,
                                        amplitudeColor = {
                                           replayUiState1.amplitudeColor(it)
                                        },
                                    )
                                },
                                onFilterTopic = {topic ->
                                    onFilterAction(
                                        FilterEchoAction.ToggleSelectTopic(topic)
                                    )
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}