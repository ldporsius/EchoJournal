package nl.codingwithlinda.echojournal.feature_record.presentation.components.quick_mode

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.AddRecordingComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.CancelRecordingButton
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission.AskPermissionComponent
import nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission.PermissionDeclinedDialog
import kotlin.math.roundToInt

@Composable
fun RecordingModeQuickComponent(
    onTap: () -> Unit,
    onAction: (RecordQuickAction) -> Unit,
    modifier: Modifier = Modifier) {

    var hasRecordAudioPermission by remember {
        mutableStateOf(true)
    }
    var inLongPressMode by remember {
        mutableStateOf(false)
    }
    var dragOffset by remember {
        mutableFloatStateOf(0f)
    }

    var cancelButtonPosition by remember {
        mutableStateOf(Offset.Zero)
    }
    var dragPosition by remember {
        mutableFloatStateOf(0f)
    }

    val dragRange =
        (cancelButtonPosition.x .. cancelButtonPosition.x + 250f)

    val isInsideDragRange = dragPosition < dragRange.endInclusive

    val cancelButtonSize = animateFloatAsState(
        if (isInsideDragRange) 1.5f else 1f,
        label = ""
    )

    fun cancelOrSave(){
        if (!inLongPressMode) return

        val isInsideDragRange1 = dragPosition < dragRange.endInclusive

        if (isInsideDragRange1) {
            onAction(RecordQuickAction.CancelRecording)
        }
        if (!isInsideDragRange1) {
            onAction(RecordQuickAction.MainButtonReleased)
        }
        inLongPressMode = false
        dragOffset = 0f
        dragPosition = 0f

    }
    AskPermissionComponent(
        hasPermission = {
            hasRecordAudioPermission = it
        }
    )
    if (!hasRecordAudioPermission) {
        PermissionDeclinedDialog(
            isPermanentlyDeclined = false,
            onConfirm = { /*todo*/},
            onDismiss = {/*todo*/ }
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                val halfWidth = it.size.width / 2
                cancelButtonPosition = Offset(halfWidth.toFloat(), 0f)
            },
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if(hasRecordAudioPermission) {

            AnimatedVisibility(inLongPressMode) {
                CancelRecordingButton(
                    modifier = Modifier
                        .size(
                            48.dp * cancelButtonSize.value
                        ),
                    onAction = {}
                )
            }

            Spacer(modifier = Modifier.width(72.dp))

            Box(
                modifier = Modifier
                    .size(72.dp)
            ) {

                if (inLongPressMode) {
                    RecordingBusyComponent(
                        modifier = Modifier
                            .size(72.dp)
                    )
                }

                if (!inLongPressMode) {
                    AddRecordingComponent(
                        modifier = Modifier
                            .size(72.dp)
                    )
                }

                //overlay to enable dragging while button stays in place
                Box(modifier = Modifier
                    .size(72.dp)
                    .offset {
                        IntOffset(
                            x = dragOffset.roundToInt(),
                            y = 0
                        )
                    }
                    .pointerInput(inLongPressMode) {
                        routePointerChangesTo(
                            onDown = {
                                println("RecordingBusyComponent: onDown")

                            },
                            onMove = {
                                dragOffset += it.position.x

                            },
                            onUp = {
                                println("RecordingBusyComponent: onUp ${it.position.x}")
                                cancelOrSave()
                            }
                        )

                    }
                    .pointerInput(inLongPressMode) {
                        this.detectTapGestures(
                            onLongPress = {
                                println("RecordingBusyComponent: onLongPress")
                                inLongPressMode = true
                                onAction(RecordQuickAction.MainButtonLongPress)
                            },
                            onTap = {
                                println("RecordingBusyComponent: onTap")
                                onTap()
                            }
                        )
                    }
                    .onGloballyPositioned {
                        val lTS = it.localToScreen(Offset.Zero)
                        dragPosition = lTS.x

                    }
                ) {
                    Spacer(
                        Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

suspend fun PointerInputScope.routePointerChangesTo(
    onDown: (PointerInputChange) -> Unit = {},
    onMove: (PointerInputChange) -> Unit = {},
    onUp: (PointerInputChange) -> Unit = {}
) {
    awaitEachGesture {
        do {
            val event = awaitPointerEvent()
            event.changes.forEach {
                when (event.type) {
                    PointerEventType.Press -> onDown(it)
                    PointerEventType.Move -> onMove(it)
                    PointerEventType.Release -> onUp(it)
                }
                it.consume()
            }
        } while (event.changes.any { it.pressed })
    }
}
