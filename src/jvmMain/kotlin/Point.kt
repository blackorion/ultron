import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

class Point(private val offset: Offset) {
    fun render(scope: DrawScope, prev: Point?) {
        scope.drawRect(
            topLeft = Offset(
                x = offset.x - 4,
                y = offset.y - 4
            ),
            size = Size(
                width = 8f,
                height = 8f
            ),
            color = Color.Blue
        )

        if (prev === null) return

        scope.drawLine(
            start = prev.offset,
            end = offset,
            color = Color.Black
        )
    }

    fun description(): String {
        return "x: ${offset.x}, y: ${offset.y}"
    }
}