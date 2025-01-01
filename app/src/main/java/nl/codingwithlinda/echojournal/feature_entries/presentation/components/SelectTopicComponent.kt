package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

@Composable
fun SelectTopicComponent(
    modifier: Modifier = Modifier,
    topics: List<UiTopic>,
    onTopicSelected: (UiTopic) -> Unit,
    onDismiss: () -> Unit
) {


    val paddingMargin = 4.dp
    DropdownMenu(
        modifier = Modifier

            .background(color = Color.Green)
            .fillMaxWidth(.98f)
            .background(
                color = MaterialTheme.colorScheme.surfaceBright,
            )
            .padding(horizontal = 8.dp),
        expanded = true,
        onDismissRequest = {
            onDismiss()
        },
        offset = DpOffset(
            x = paddingMargin,
            y = 0.dp
        )
    ) {

        topics.forEach {

            val bgColor = if (it.isSelected) {
                MaterialTheme.colorScheme.surfaceDim
            } else {
                Color.Transparent
            }
            DropdownMenuItem(
                modifier = Modifier.background(
                    color = bgColor,
                    shape = CircleShape
                ),
                text = {
                        Text(it.name)
                },
                onClick = {
                    onTopicSelected(it)
                },
                leadingIcon = {
                    Text("#")
                },
                trailingIcon = {
                    if (it.isSelected) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                },
                colors = MenuDefaults.itemColors(

                )
            )

        }

    }
}