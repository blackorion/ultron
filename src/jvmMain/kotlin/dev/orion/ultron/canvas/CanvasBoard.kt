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

val canvasColor = Color.White
val canvasSize = 400.dp

@Composable
fun CanvasBoard(shapes: List<Shape>, onClick: () -> Unit, onMove: (Offset) -> Unit) {
    var mousePointer by remember { mutableStateOf(Offset.Zero) }
    var inFocus by remember { mutableStateOf(false) }

    Box {
        InternalCanvas(
            shapes = shapes,
            onClick = onClick,
            onFocus = { inFocus = true },
            onBlur = { inFocus = false },
            onMove = {
                mousePointer = it
                onMove(it)
            }
        )
        if (inFocus)
            Row {
                Text("x: ${mousePointer.x}")
                Text("y: ${mousePointer.y}")
            }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InternalCanvas(
    shapes: List<Shape>, onMove: (Offset) -> Unit,
    onFocus: () -> Unit = {},
    onBlur: () -> Unit = {},
    onClick: () -> Unit
) {
    val intersectionSource = MutableInteractionSource()

    Canvas(
        modifier = Modifier
            .size(canvasSize)
            .background(canvasColor)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = intersectionSource
            )
            .onPointerEvent(PointerEventType.Move) {
                val position = it.changes.first().position
                onMove(position)
            }
            .onPointerEvent(PointerEventType.Enter) {
                onFocus()
            }
            .onPointerEvent(PointerEventType.Exit) {
                onBlur()
            }
    ) {
        drawRect(size = Size(canvasSize.toPx(), canvasSize.toPx()), color = canvasColor)

        shapes.windowed(2, 1) { (current, previous) ->
            current.render(this, previous)
        }
    }
}