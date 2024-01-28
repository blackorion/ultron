package dev.orion.ultron.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

val canvasSize = 400.dp

@Composable
fun CanvasBoard(modifier: Modifier = Modifier, canvasState: CanvasState) {
    var mousePointer by remember { mutableStateOf(Offset.Zero) }
    var inFocus by remember { mutableStateOf(false) }
    val commandsPath = canvasState.commandsPath
    val points = canvasState.points

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .clipToBounds()
    ) {
        InternalCanvas(
            onClick = { canvasState.onClick() },
            onFocus = { inFocus = true },
            onBlur = { inFocus = false },
            onMove = {
                mousePointer = it
                canvasState.onMouseMove(it)
            }
        ) {
            drawPath(commandsPath, color = Color.Black, style = Stroke(2f))
            drawPoints(points, pointMode = PointMode.Points, color = Color.Black, strokeWidth = 8f)

            canvasState.selectedPoints.forEach { point ->
                drawCircle(
                    color = Color.Red,
                    radius = 8f,
                    center = point,
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
            .onPointerEvent(PointerEventType.Move) {
                onMove(it.changes.first().position)
            }
            .onPointerEvent(PointerEventType.Enter) {
                onFocus()
            }
            .onPointerEvent(PointerEventType.Exit) {
                onBlur()
            },
        onDraw = {
            val borderWidthPx = 1.dp.toPx()

            drawRect(
                color = Color.LightGray,
                topLeft = Offset(0f, 0f),
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