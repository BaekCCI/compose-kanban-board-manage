package woowacourse.kanban.board.ui.component

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow

@Composable
fun DraggableContainer(
    modifier: Modifier = Modifier,
    onDragStart: () -> Unit = {},
    onDragChange: (Offset) -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragCancel: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    var contentWindowPosition by remember { mutableStateOf(Offset.Zero) }
    Box(
        modifier = modifier.onGloballyPositioned { contentWindowPosition = it.positionInWindow() }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { onDragStart() },
                    onDrag = { change, _ ->
                        change.consume()
                        onDragChange(contentWindowPosition + change.position)
                    },
                    onDragEnd = { onDragEnd() },
                    onDragCancel = { onDragCancel() },
                )
            },
    ) {
        content()
    }
}
