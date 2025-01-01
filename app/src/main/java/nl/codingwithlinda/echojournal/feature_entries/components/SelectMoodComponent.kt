package nl.codingwithlinda.echojournal.feature_entries.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.R

@Composable
fun SelectMoodComponent(
    modifier: Modifier = Modifier,
    onMoodSelected: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SelectMoodItem(
            icon = {
                Image(painter = painterResource(id = R.drawable.mood_exited), contentDescription = null)
            },
            text = { Text("Exited")},
            onClick = { }
        )
        SelectMoodItem(
            icon = {
                Image(painter = painterResource(id = R.drawable.mood_peaceful), contentDescription = null)
            },
            text = { Text("Peaceful")},
            onClick = { }
        )
        SelectMoodItem(
            icon = {
                Image(painter = painterResource(id = R.drawable.mood_neutral), contentDescription = null)
            },
            text = { Text("Neutral")},
            onClick = { }
        )
        SelectMoodItem(
            icon = {
                Image(painter = painterResource(id = R.drawable.mood_sad), contentDescription = null)
            },
            text = { Text("Sad")},
            onClick = { }
        )
        SelectMoodItem(
            icon = {
                Image(painter = painterResource(id = R.drawable.mood_stressed), contentDescription = null)
            },
            text = { Text("Stressed")},
            onClick = { }
        )

    }
}

@Composable
fun SelectMoodItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        icon()
        text()
    }
}