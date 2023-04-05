package canvas

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset

class SchemaState(val commands: Commands) {
    private var handler: CanvasEventHandler by mutableStateOf(IdleCanvasEventHandler)

    var mousePosition by mutableStateOf(Offset.Zero)

    val shapes @Composable get() = commands.list

    fun onClick() {
        handler.handleClick(mousePosition, commands)
    }

    fun switchTo(handler: CanvasEventHandler) {
        this.handler = handler
    }

    fun isHandledBy(handler: DrawCanvasEventHandler): Boolean {
        return this.handler === handler
    }

    fun resetHandler() {
        handler = IdleCanvasEventHandler
    }

    fun clear() {
        commands.clear()
    }

    fun onMouseMove(position: Offset) {
        mousePosition = position
    }
}

@Composable
fun rememberSchemaState(commands: Commands) = remember(commands) { SchemaState(commands) }