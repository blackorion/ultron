package dev.orion.ultron.canvas

import androidx.compose.ui.geometry.Offset

interface CanvasEventHandler {
    fun init()
    fun destroy()
    fun handleClick(mousePosition: Offset)
    fun handleMove(mousePosition: Offset)
}

open class DefaultCanvasEventHandler : CanvasEventHandler {
    override fun init() {}
    override fun destroy() {}
    override fun handleClick(mousePosition: Offset) {}
    override fun handleMove(mousePosition: Offset) {}
}

class IdleCanvasEventHandler(private val state: CanvasState) : DefaultCanvasEventHandler() {
    override fun handleClick(mousePosition: Offset) {
        val box = HitBox(mousePosition)

        state.apply(CanvasActions.FocusItem(box))
    }

    override fun handleMove(mousePosition: Offset) {}
}

class DrawCanvasEventHandler(private val canvas: CanvasState) : DefaultCanvasEventHandler() {
    override fun handleClick(mousePosition: Offset) {
        canvas.apply(CanvasActions.AddPoint(mousePosition))
    }
}

class EditCanvasEventHandler(private val canvas: CanvasState) : DefaultCanvasEventHandler() {
    override fun handleClick(mousePosition: Offset) {
        val bounds = HitBox(mousePosition)
    }
}