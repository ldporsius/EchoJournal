package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.core.presentation.components.EchoPlaybackComponent
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.ReplayEchoAction
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiEcho

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EchoListItemContent(
    modifier: Modifier = Modifier,
    uiEcho: UiEcho,
   replayComponent: @Composable () -> Unit,
) {

    val title = uiEcho.name
    val timeStamp = uiEcho.timeStamp

    val tags = uiEcho.topics


    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
            ,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Text(title)
            Text(timeStamp)
        }


        replayComponent()


        EchoDescriptionComponent(
            description = uiEcho.description,
        )

        FlowRow(
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            tags.forEach {
                Row (modifier = Modifier
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(100))
                    .background(color = Color.LightGray)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                ) {
                    Text("#")
                    Text(it)
                }
            }
        }
    }
}
