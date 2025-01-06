package nl.codingwithlinda.echojournal.feature_create.presentation.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood

data class CreateEchoUiState(
    val title: String = "",
    //val topic: String = "",
    //val topics: List<String> = listOf(),
    //val isTopicsExpanded: Boolean = false,
    val moods: List<UiMood> = emptyList(),
    val selectedMood: UiMood? = null,
    val isSelectMoodExpanded: Boolean = false,
    val confirmedMood: UiMood? = null,
)
{
    @Composable
    fun SelectedMoodIcon(){
        confirmedMood?.let {
            Image(
                painter = painterResource(it.icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }

        if (confirmedMood == null){
            Icon(
                Icons.Default.AddCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.size(32.dp)
            )
        }

    }
}