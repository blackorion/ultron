package dev.orion.ultron.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp

val canvasColor = Color.White
val canvasSize = 400.dp

@Composable
fun CanvasBoard(canvasState: CanvasState) {
    var mousePointer by remember { mutableStateOf(Offset.Zero) }
    var inFocus by remember { mutableStateOf(false) }
    val commandsPath = canvasState.commandsPath
    val points = canvasState.points

    Box {
        InternalCanvas(
            onClick = { canvasState.onClick() },
            onFocus = { inFocus = true },
            onBlur = { inFocus = false },
            onMove = {
                mousePointer = it
                canvasState.onMouseMove(it)
            }
        ) {
            drawRect(size = Size(canvasSize.toPx(), canvasSize.toPx()), color = canvasColor)

            drawPath(commandsPath, color = Color.Black, style = Stroke(2f))

            points.forEach {
                drawCircle(color = Color.Black, radius = 4f, center = it)
            }
        }

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
    modifier: Modifier = Modifier,
    onMove: (Offset) -> Unit,
    onFocus: () -> Unit = {},
    onBlur: () -> Unit = {},
    onClick: () -> Unit,
    onDraw: DrawScope.() -> Unit,
) {
    val intersectionSource = MutableInteractionSource()

    Canvas(
        modifier = modifier
            .size(canvasSize)
            .background(canvasColor)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = intersectionSource,
            )
            .onPointerEvent(PointerEventType.Move) {
                onMove(it.changes.first().position)
            }
            .onPointerEvent(PointerEventType.Enter) {
                onFocus()
            }
            .onPointerEvent(PointerEventType.Exit) {
                onBlur()
            },
        onDraw
    )
}