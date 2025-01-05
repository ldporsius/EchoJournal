package nl.codingwithlinda.echojournal.feature_entries.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle


@Composable
fun EchoDescriptionComponent(
    //modifier: Modifier = Modifier.fillMaxWidth(),
    description: String,
    collapsedMaxLine: Int = 3

) {
    val showMoreStyle: SpanStyle = SpanStyle(color = Color.Blue, fontWeight = FontWeight.W500)
    val showLessStyle: SpanStyle = SpanStyle(color = Color.Blue, fontWeight = FontWeight.W500)

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var isClickable by remember {
        mutableStateOf(false)
    }
    /*val textLayoutResultState = remember {
        mutableStateOf<TextLayoutResult?>(null)
    }*/
    //val textLayoutResult = textLayoutResultState.value
    var lastCharacterIndex by remember { mutableStateOf(0) }

    var finalText: AnnotatedString by remember {
        mutableStateOf(buildAnnotatedString {
            append(description)
        })
    }
    val showMoreText = "...Show more"
    val showLessText = "...Show less"

    //val maxLines = if (isExpanded) Int.MAX_VALUE else 3

    /* LaunchedEffect(textLayoutResult) {
         if (textLayoutResult == null) return@LaunchedEffect

         if (isExpanded){
             finalText = buildAnnotatedString {
                 append(description)
                 withLink(
                     link = LinkAnnotation.Clickable(
                         tag = "Show Less",
                         linkInteractionListener = { isExpanded = !isExpanded }
                     )
                 ){
                     withStyle(SpanStyle(color = Color.Blue)){
                         append(showLessText)
                     }
                 }

             }
             return@LaunchedEffect
         }
         println("Text layout result changed:hasVisualOverflow =  ${textLayoutResult.hasVisualOverflow}")

         if ( textLayoutResult.hasVisualOverflow) {

             val lastCharIndex = (textLayoutResult.layoutInput.text.lastIndex).coerceAtMost(description.length)

             println("Original text lenght: ${description.length}")
             println("Last char index: $lastCharIndex")

             val adjustedText =  description.substring(0, lastCharIndex)
                 .dropLast(showMoreText.length)
                 .dropLastWhile {
                     it == ' ' || it == '.'
                 }
             println("adjusted text: $adjustedText")

             finalText = buildAnnotatedString {
                 append(adjustedText)
                 withLink(
                     link = LinkAnnotation.Clickable(
                         tag = "Show More",
                         linkInteractionListener = { isExpanded = !isExpanded }
                     )
                 ){
                     withStyle(SpanStyle(color = Color.Blue)){
                         append(showMoreText)
                     }
                 }
             }
         }
     }*/


    val annotatedText = buildAnnotatedString {
        if (isClickable) {
            if (isExpanded) {
                append(description)
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = "",
                        linkInteractionListener = { isExpanded = !isExpanded }
                    )
                ) {
                    withStyle(style = showLessStyle) {
                        append(showLessText)
                    }
                }
            } else {
                val adjustText = description.substring(startIndex = 0, endIndex = lastCharacterIndex)
                    .dropLast(showMoreText.length)
                    .dropLastWhile { it.isWhitespace() || it == '.' }
                append(adjustText)
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = "Tag",
                        linkInteractionListener = { isExpanded = !isExpanded }
                    )
                ) {
                    withStyle(style = showMoreStyle) {
                        append(showMoreText)
                    }
                }
            }
        } else {
            append(description)
        }
    }


    Text(
        text = annotatedText,
        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
        maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
        // overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
        modifier = Modifier.clickable { isExpanded = !isExpanded },
        onTextLayout = {textLayoutResult ->
            //textLayoutResultState.value = textLayoutResult
            if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                isClickable = true
                lastCharacterIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
            }
        }
    )
}