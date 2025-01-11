package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.toSize
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_create.presentation.state.PlaybackState
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction

@Composable
fun PlaybackIcon(
    modifier: Modifier = Modifier,
    id: String,
    uri: String,
    moodColorPlayed: Color,
    playbackState: PlaybackState,
    onAction: (ReplayEchoAction) -> Unit
) {
    var playIconSize by remember {
        mutableStateOf(Size.Zero)
    }

    @Composable
    fun icon() = when(playbackState){
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
    IconButton(onClick = {
          onAction(ReplayEchoAction.Play(id, uri))
      },
          modifier = Modifier
              .onSizeChanged {
                  playIconSize = it.toSize()
              },
          colors = androidx.compose.material3.IconButtonDefaults.iconButtonColors(
              containerColor = androidx.compose.ui.graphics.Color.White,
              contentColor = moodColorPlayed
          )
      ) {
        icon()
      }
}