package dev.orion.ultron.canvas

import androidx.compose.ui.geometry.Offset

class HitBox(offset: Offset) {
    private val size = 4;
    private val topLeft = Offset(x = (offset.x - size), y = (offset.y - size))
    private val bottomRight = Offset(x = (offset.x + size), y = (offset.y + size))

    fun contains(offset: Offset): Boolean {
        return offset.x >= topLeft.x
                && offset.x <= bottomRight.x
                && offset.y >= topLeft.y
                && offset.y <= bottomRight.y
    }
}