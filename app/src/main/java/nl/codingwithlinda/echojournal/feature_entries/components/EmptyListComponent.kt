package nl.codingwithlinda.echojournal.feature_entries.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.R

@Composable
fun EmptyListComponent(modifier: Modifier = Modifier) {
    Box(modifier = modifier,
        contentAlignment = androidx.compose.ui.Alignment.Center
    ){
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.empty_list_icon),
                contentDescription = "Empty list"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No entries",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(text = "Start recording your first entry")
        }
    }
}