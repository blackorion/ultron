package dev.orion.ultron.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

class Shape(private val offset: Offset) {
    private var inFocus: Boolean = false
    private var isActive: Boolean = false
    private val defaultSize = 8f

    val position: Offset
        get() = Offset(x = offset.x, y = offset.y)

    fun render(scope: DrawScope, prev: Shape?) {
        println("render shape")

        renderPoint(scope)

        if (prev != null) renderEdge(scope, prev)
    }

    private fun renderPoint(scope: DrawScope) {
        val size = if (inFocus) defaultSize * 2 else defaultSize
        val padding = size / 2

        scope.drawRect(
            topLeft = Offset(
                x = offset.x - padding, y = offset.y - padding
            ), size = Size(
                width = size, height = size
            ), color = if (inFocus) Color.Red
            else if (isActive) Color.Green
            else Color.Blue
        )
    }

    private fun renderEdge(scope: DrawScope, prev: Shape) {
        scope.drawLine(
            start = prev.offset, end = offset, color = Color.Black
        )
    }

    fun description(): String = "x: ${offset.x}, y: ${offset.y}"

    fun focus() {
        inFocus = true
    }

    fun blur() {
        inFocus = false
    }

    fun activate() {
        isActive = true
    }

    fun deactivate() {
        isActive = false
    }
}