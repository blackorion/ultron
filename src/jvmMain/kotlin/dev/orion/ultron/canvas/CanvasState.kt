package dev.orion.ultron.canvas

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import dev.orion.ultron.Commands
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CanvasState(val commands: Commands) {
    private val scope = CoroutineScope(Dispatchers.Default)
    private var handler: CanvasEventHandler by mutableStateOf(IdleCanvasEventHandler(this))
    private var mousePosition by mutableStateOf(Offset.Zero)

    val shapes @Composable get() = commands.list

    fun onClick() {
        handler.handleClick(mousePosition)
    }

    fun onMouseMove(position: Offset) = scope.launch {
        mousePosition = position
        handler.handleMove(position)
    }

    fun switchTo(handler: CanvasEventHandler) {
        if (handler === this.handler)
            return

        this.handler.destroy()
        this.handler = handler
        this.handler.init()
    }

    fun isHandledBy(clazz: Class<out CanvasEventHandler>): Boolean {
        return this.handler.javaClass === clazz
    }

    fun resetHandler() {
        handler = IdleCanvasEventHandler(this)
    }

    fun clear() {
        commands.clear()
    }
}

@Composable
fun rememberSchemaState(commands: Commands) = remember(commands.list) { CanvasState(commands) }