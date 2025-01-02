package nl.codingwithlinda.echojournal.feature_entries.presentation.state

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
}
