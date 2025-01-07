package nl.codingwithlinda.echojournal.feature_create.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.components.GradientButton
import nl.codingwithlinda.echojournal.feature_create.presentation.state.CreateEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.ui.theme.ColorFamily
import nl.codingwithlinda.echojournal.ui.theme.buttonDisabledGradient
import nl.codingwithlinda.echojournal.ui.theme.buttonGradient

@Composable
fun SelectMoodBottomSheetContent(
    modifier: Modifier = Modifier,
    moods: List<UiMood>,
    selectedMood: UiMood?,
    onAction: (CreateEchoAction) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text("How are you doing?",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 48.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            moods.forEach {
                MoodItemVertical(
                    modifier = Modifier
                        .clickable {
                            onAction(CreateEchoAction.SelectMood(it))
                        },
                    it)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalButton (
                onClick = {
                    onAction(CreateEchoAction.ShowHideMoods(false))
                },
            ) {
                Text("Cancel")
            }

            GradientButton(
                text = {
                    Row {
                        Icon(imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(end = 8.dp)
                        )
                        Text("Confirm")
                    }
                },
                gradient = if (selectedMood != null) buttonGradient else buttonDisabledGradient,
                onClick = {
                    selectedMood?.let {
                        onAction(CreateEchoAction.ConfirmMood(it))
                    }
                    onAction(CreateEchoAction.ShowHideMoods(false))
                },
                enabled = selectedMood != null,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun MoodItemVertical(
    modifier: Modifier = Modifier,
    mood: UiMood,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = mood.icon),
            contentDescription = null,
        )
        Text(mood.name.asString(),
            style = MaterialTheme.typography.labelSmall
        )
    }
}