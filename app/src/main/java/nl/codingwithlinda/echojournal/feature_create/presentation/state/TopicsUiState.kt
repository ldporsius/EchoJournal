package nl.codingwithlinda.echojournal.feature_create.presentation.state

import nl.codingwithlinda.core.model.Topic

data class TopicsUiState(
    val searchText: String = "",
    val topics: List<Topic> = emptyList(),
    val isExpanded: Boolean = false
){
    fun shouldShowCreate(): Boolean{
        return searchText.uppercase() !in topics.map { it.name.uppercase() } && searchText.isNotBlank()
    }
}
