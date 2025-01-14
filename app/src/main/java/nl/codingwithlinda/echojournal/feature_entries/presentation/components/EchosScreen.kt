package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.EchoesUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.FilterEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.TopicsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho
import nl.codingwithlinda.echojournal.ui.theme.backgroundGradient


@Composable
fun EchosScreen(
    echoesUiState: EchoesUiState,
    moodsUiState: MoodsUiState,
    topicsUiState: TopicsUiState,
    replayUiState: (uiEcho: UiEcho) -> ReplayUiState,
    onFilterAction: (FilterEchoAction) -> Unit,
    onReplayAction: (ReplayEchoAction) -> Unit,
    recordingComponent: @Composable () -> Unit,
    navToSettings: () -> Unit
) {

    Scaffold(
        modifier = Modifier.safeContentPadding(),
        topBar = { EchosTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            navToSettings = navToSettings
        ) },

    ) { padding ->
        Box(modifier =  Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient)
            .padding(padding))
        {
            Column(
                modifier = Modifier
            ) {

                if (echoesUiState.echoesTotal == 0) {
                    EmptyListComponent(
                        Modifier.fillMaxSize()
                    )
                } else {
                    EchoListComponent(
                        entries = echoesUiState.selectedEchoes,
                        moodsUiState = moodsUiState,
                        topicsUiState = topicsUiState,
                        replayUiState = {
                            replayUiState(it)
                        },
                        onFilterAction = onFilterAction,
                        onReplayAction = onReplayAction,
                    )
                }
            }

            Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                recordingComponent()
            }
        }
    }
}