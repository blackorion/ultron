package dev.orion.ultron.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CanvasBoard(shapes: List<Shape>, onClick: () -> Unit, onMove: (Offset) -> Unit) {
    val color = Color.White
    val size = 400.dp
    val intersectionSource = remember { MutableInteractionSource() }
    var mousePointer by remember { mutableStateOf(Offset.Zero) }
    var inFocus by remember { mutableStateOf(false) }

    Box {
        Canvas(
            modifier = Modifier
                .size(size)
                .background(color)
                .clickable(
                    onClick = onClick,
                    indication = null,
                    interactionSource = intersectionSource
                )
                .onPointerEvent(PointerEventType.Move) {
                    val position = it.changes.first().position
                    mousePointer = position
                    onMove(position)
                }
                .onPointerEvent(PointerEventType.Enter) {
                    inFocus = true
                }
                .onPointerEvent(PointerEventType.Exit) {
                    inFocus = false
                }
        ) {
            val scope = this
            drawRect(size = Size(size.toPx(), size.toPx()), color = color)

            shapes.forEachIndexed { ix, point ->
                val prev = if (ix > 0) shapes[ix - 1] else null
                point.render(scope, prev)
            }
        }

        if (inFocus)
            Row {
                Text("x: ${mousePointer.x}")
                Text("y: ${mousePointer.y}")
            }
    }
}