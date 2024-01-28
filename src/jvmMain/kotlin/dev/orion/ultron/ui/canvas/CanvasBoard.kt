package dev.orion.ultron.ui.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun CanvasBoard(modifier: Modifier = Modifier, canvasState: CanvasState) {
    Box(modifier = modifier.clipToBounds()) {
        var mousePointer by remember { mutableStateOf(Offset.Zero) }
        var inFocus by remember { mutableStateOf(false) }
        var showMenu by remember { mutableStateOf(false) }

        InternalCanvas(
            onClick = { canvasState.onClick() },
            onRightClick = {
                showMenu = true
            },
            onFocus = { inFocus = true },
            onBlur = { inFocus = false },
            onMove = {
                mousePointer = it
                canvasState.onMouseMove(it)
            }
        ) {

            drawPath(canvasState.commandsPath, color = Color.Black, style = Stroke(2f))
            drawPoints(canvasState.points, pointMode = PointMode.Points, color = Color.Black, strokeWidth = 8f)

            canvasState.selectedPoints.forEach { point ->
                drawCircle(
                    color = Color.Red,
                    radius = 8f,
                    center = point,
                )
            }
        }

        if (showMenu)
            Box(modifier = Modifier.offset { IntOffset(mousePointer.x.toInt(), mousePointer.y.toInt()) }) {
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.width(200.dp)
                ) {
                    DropdownMenuItem(
                        onClick = { showMenu = false },
                        text = { Text("место для меню") }
                    )
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
    onRightClick: () -> Unit,
    onDraw: DrawScope.() -> Unit,
) {
    val intersectionSource = MutableInteractionSource()

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = intersectionSource,
            )
            .onPointerEvent(PointerEventType.Move) { onMove(it.changes.first().position) }
            .onPointerEvent(PointerEventType.Enter) { onFocus() }
            .onPointerEvent(PointerEventType.Exit) { onBlur() }
            .onPointerEvent(PointerEventType.Press) {
                if (it.buttons.isSecondaryPressed)
                    onRightClick()
            },
        onDraw = {
            val borderWidthPx = 1.dp.toPx()

            drawRect(
                color = Color.LightGray,
                topLeft = Offset.Zero,
                size = size,
                style = Stroke(borderWidthPx)
            )

            val vNum = (size.width / 100).toInt()
            val verticalStrokes = if (size.width % vNum > 0) vNum + 1 else vNum

            repeat(verticalStrokes) { ix ->
                val start = ix * 100f
                drawLine(
                    start = Offset(start, 0f),
                    end = Offset(start, size.height),
                    color = Color.LightGray,
                    alpha = 0.5f,
                    strokeWidth = borderWidthPx
                )
            }

            val hNum = (size.height / 100).toInt()
            val horizontalStrokes = if (size.height % hNum > 0) hNum + 1 else hNum

            repeat(horizontalStrokes) { ix ->
                val start = ix * 100f
                drawLine(
                    start = Offset(0f, start),
                    end = Offset(size.width, start),
                    color = Color.LightGray,
                    alpha = 0.5f,
                    strokeWidth = borderWidthPx
                )
            }

            onDraw()
        }
    )
}