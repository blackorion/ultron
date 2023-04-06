package dev.orion.ultron.canvas

import androidx.compose.ui.geometry.Offset
import dev.orion.ultron.Commands

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

class IdleCanvasEventHandler(private val canvas: CanvasState) : DefaultCanvasEventHandler() {
    override fun handleClick(offset: Offset) {}

    override fun handleMove(mousePosition: Offset) {
        val box = HitBox(mousePosition)

        canvas.commands.list.forEach {
            if (box.contains(it.position)) it.focus()
            else it.blur()
        }
    }
}

class DrawCanvasEventHandler(private val canvas: CanvasState) : DefaultCanvasEventHandler() {
    override fun handleClick(offset: Offset) {
        canvas.commands.add(Shape(offset))
    }
}

class EditCanvasEventHandler(private val canvas: CanvasState) : DefaultCanvasEventHandler() {
    override fun handleClick(offset: Offset) {
        val bounds = HitBox(offset)

        canvas.commands.list
            .find { bounds.contains(it.position) }
            .also { if (it !== null) it.activate() }
    }
}