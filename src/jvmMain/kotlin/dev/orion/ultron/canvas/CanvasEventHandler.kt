package dev.orion.ultron.canvas

import androidx.compose.ui.geometry.Offset

interface CanvasEventHandler {
    fun init()
    fun destroy()
    fun handleClick(offset: Offset)
    fun handleMove(mousePosition: Offset)
}

open class DefaultCanvasEventHandler : CanvasEventHandler {
    override fun init() {}
    override fun destroy() {}
    override fun handleClick(offset: Offset) {}
    override fun handleMove(mousePosition: Offset) {}
}

class IdleCanvasEventHandler(private val state: CanvasState) : DefaultCanvasEventHandler() {
    override fun handleClick(offset: Offset) {}

    override fun handleMove(mousePosition: Offset) {
        val box = HitBox(mousePosition)

        state.apply(CanvasActions.FocusItem(box))
    }
}

class DrawCanvasEventHandler(private val canvas: CanvasState) : DefaultCanvasEventHandler() {
    override fun handleClick(offset: Offset) {
        canvas.apply(CanvasActions.AddPoint(offset))
    }
}

class EditCanvasEventHandler(private val canvas: CanvasState) : DefaultCanvasEventHandler() {
    override fun handleClick(offset: Offset) {
        val bounds = HitBox(offset)
    }
}