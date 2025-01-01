package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EchoListItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit
) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.Top

    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(48.dp)
            ,
           contentAlignment = Alignment.TopCenter
        ) {

            VerticalDivider(
                modifier = Modifier
                   ,
                thickness = 1.dp
            )
            icon()
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 16.dp)
            ,
        ) {
            ElevatedCard(
                modifier = Modifier
                    .weight(1f)
                , colors = CardDefaults.elevatedCardColors(
                    containerColor = androidx.compose.ui.graphics.Color.White
                )

            ) {
                content()
            }
        }

    }
}