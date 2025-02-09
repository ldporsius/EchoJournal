package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.feature_entries.presentation.state.MoodsUiState
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood
import nl.codingwithlinda.echojournal.ui.theme.primary20
import nl.codingwithlinda.echojournal.ui.theme.surfaceTint

@Composable
fun SelectMoodComponent(
    modifier: Modifier = Modifier,
    moodsUiState: MoodsUiState,
    onMoodSelected: (UiMood) -> Unit
) {
    val moods = moodsUiState.moods

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        moods
            .sortedByDescending { it.mood.sortOrder }
            .forEach {
            val isSelected = moodsUiState.isSelected(it)
            val bgColor =
                if (isSelected) surfaceTint.copy(.05f) else Color.Transparent
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = bgColor, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                SelectMoodItem(
                    modifier = Modifier,
                    icon = {
                        Image(
                            painter = painterResource(id = it.icon),
                            contentDescription = null,
                            modifier = Modifier.requiredSize(24.dp),
                            contentScale = ContentScale.FillHeight
                        )
                    },
                    text = { Text(it.name.asString() ,
                        style = MaterialTheme.typography.labelLarge
                    )
                           },
                    selectedIcon = {
                        if (isSelected) {
                            Icon(imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = primary20
                            )
                        }
                        else Unit
                    },
                    onClick = {
                        onMoodSelected(it)
                    }
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Composable
fun SelectMoodItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        icon()
        text()
        Spacer(modifier = Modifier.weight(1f))
        selectedIcon()
    }
}