package nl.codingwithlinda.echojournal.feature_settings.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.echojournal.core.presentation.mappers.blankMoods

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsRoot(
    navBack: () -> Unit
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Settings")
                },
                navigationIcon = {
                    IconButton(
                        onClick = navBack
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        SettingsScreen(
            modifier = Modifier.padding(paddingValues),
            moods = blankMoods.values.toList(),
            topic = "todo"
        )
    }
}