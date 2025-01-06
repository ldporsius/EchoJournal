package nl.codingwithlinda.echojournal.feature_create.presentation.state

data class TopicsUiState(
    val searchText: String = "",
    val topics: List<String> = emptyList(),
    val isExpanded: Boolean = false
){
    fun shouldShowCreate(): Boolean{
        return searchText.uppercase() !in topics.map { it.uppercase() } && searchText.isNotBlank()
    }
}
