package nl.codingwithlinda.echojournal.feature_entries.presentation.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiMood

data class MoodsUiState(
    val moods: List<UiMood>,
    val selectedMoods: List<UiMood>
){
    fun shouldShowClearSelection(): Boolean
         = selectedMoods.isNotEmpty()

    fun isSelected(mood: UiMood): Boolean{
        return mood in selectedMoods
    }

    @Composable
    fun SelectedMoodsLabel(){
        val imageSize = DpSize(36.dp, 36.dp)

        val overlapFactor = 0.5f
        val overlap = imageSize.width * overlapFactor

        val iconSizes = remember(selectedMoods) {
            selectedMoods.mapIndexed { index, _ ->
                imageSize * (1f - index * 0.05f)
            }
        }

        if (selectedMoods.isEmpty()) {
            Text("All moods")
        }
        selectedMoods.forEachIndexed { index, mood ->
            Image(
                painter = painterResource(id = mood.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(iconSizes[index])
                    .offset(
                        x =  -overlap * index, y = 0.dp)
            )
        }

    }
}
