package nl.codingwithlinda.echojournal.feature_entries.presentation.util

import nl.codingwithlinda.echojournal.R
import nl.codingwithlinda.echojournal.core.presentation.util.UiText
import nl.codingwithlinda.echojournal.feature_entries.presentation.ui_model.UiTopic

fun limitTopics(topics: List<UiTopic>): UiText{

    if(topics.isEmpty()){
        return UiText.StringResource(R.string.all_topics)
    }
    
   val firstTwoTopics = topics.sortedBy { it.name }.take(2).joinToString {
       it.name
   }

    val rest = topics.size - 2

    return if(rest > 0){
        UiText.DynamicString("$firstTwoTopics +$rest")
    }else{
        UiText.DynamicString(firstTwoTopics)
    }
}