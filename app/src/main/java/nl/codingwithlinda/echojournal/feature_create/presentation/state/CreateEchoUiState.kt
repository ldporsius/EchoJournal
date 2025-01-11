package nl.codingwithlinda.echojournal.feature_create.presentation.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood

data class CreateEchoUiState(
    val title: String = "",
    val description: String = "",
    val duration: String,
    val playbackState: PlaybackState = PlaybackState.STOPPED,
    val amplitudes: List<Float> = emptyList(),
    val amplitudesPlayed: List<Int> = emptyList(),
    val moods: List<UiMood> = emptyList(),
    val selectedMood: UiMood? = null,
    val isSelectMoodExpanded: Boolean = false,
    val confirmedMood: UiMood? = null,
)
{
    fun canSave(): Boolean
         = title.isNotBlank() && confirmedMood != null

    private val selectedMoodColor: Color =  selectedMood?.let { Color(it.color) } ?: Color.DarkGray
    fun amplitudeColor(index: Int) : Color{
        return if(index in amplitudesPlayed){
           selectedMoodColor
        }
        else Color.Gray
    }

    @Composable
    fun PlaybackIcon(
        modifier: Modifier = Modifier,
                     onClick: () -> Unit
    ) {
        @Composable
        fun icon() = when (playbackState){
            PlaybackState.PLAYING -> {
                Icon(painter = painterResource(R.drawable.pause), contentDescription = null)
            }
            PlaybackState.PAUSED -> {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
            }
            PlaybackState.STOPPED -> {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
            }
        }
        IconButton(
            modifier = modifier,
            onClick = onClick ,
            colors = androidx.compose.material3.IconButtonDefaults.iconButtonColors(
                containerColor = Color.White,
                contentColor = selectedMoodColor
            )
        ) {
            icon()
        }
    }
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