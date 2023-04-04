package canvas

import Point
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun CanvasBoard(points: List<Point>, onClick: () -> Unit, onMove: (Offset) -> Unit) {
    val color = Color.White
    val size = 400.dp
    val intersectionSource = remember { MutableInteractionSource() }

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
                onMove(position)
            }
    ) {
        val scope = this
        drawRect(size = Size(size.toPx(), size.toPx()), color = color)

        points.forEachIndexed { ix, point ->
            val prev = if (ix > 0) points[ix - 1] else null
            point.render(scope, prev)
        }
    }
}