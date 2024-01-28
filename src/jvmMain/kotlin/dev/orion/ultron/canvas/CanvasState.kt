package dev.orion.ultron.canvas

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import dev.orion.ultron.Command
import dev.orion.ultron.Commands
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CanvasState(private val commands: Commands) {

    private val scope = CoroutineScope(Dispatchers.Default)
    private var handler: CanvasEventHandler by mutableStateOf(IdleCanvasEventHandler(this))
    private var mousePosition by mutableStateOf(Offset.Zero)

    val selectedPoints: List<Offset>
        get() = commands.selected()?.toListOfOffsets() ?: emptyList()

    val commandsPath: Path
        get() {
            val p = Path()
            val list = commands.list

            if (list.isNotEmpty()) {
                when (val first = list.first()) {
                    is Command.Point -> p.moveTo(first.x, first.y)
                    else -> {}
                }
            }

            list.forEachIndexed { ix, command ->
                when (command) {
                    is Command.Point -> p.lineTo(command.x, command.y)
                    is Command.CubicBezier -> p.cubicTo(
                        command.a.x,
                        command.a.y,
                        command.b.x,
                        command.b.y,
                        command.d.x,
                        command.d.y
                    )

                    is Command.Arc -> {
                        if (ix != 0)
                            p.lineTo(command.start.x, command.start.y)

                        p.addArc(
                            Rect(
                                command.p.x - command.radius,
                                command.p.y - command.radius,
                                command.p.x + command.radius,
                                command.p.y + command.radius,
                            ),
                            command.startAngle,
                            command.sweepAngle,
                        )
                    }
                }
            }

            return p
        }

    val points: List<Offset>
        get() = commands.list.flatMap(Command::toListOfOffsets)

    fun apply(action: CanvasActions) {
        when (action) {
            is CanvasActions.FocusItem -> {
                commands.list.find { it.isIn(action.hitBox) }?.let { commands.select(it) }
            }

            is CanvasActions.AddPoint -> {
                commands.add(Command.Point(action.position.x, action.position.y))
            }

            is CanvasActions.BlurItem -> commands.clearSelection()
        }
    }

    fun onClick() = scope.launch {
        handler.handleClick(mousePosition)
    }

    fun onMouseMove(position: Offset) = scope.launch {
        mousePosition = position
        handler.handleMove(position)
    }

    fun switchTo(handler: CanvasEventHandler) {
        if (handler === this.handler) return

        this.handler.destroy()
        this.handler = handler
        this.handler.init()
    }

    fun isHandledBy(clazz: Class<out CanvasEventHandler>): Boolean {
        return this.handler.javaClass === clazz
    }

    fun resetHandler() {
        val item = this

        scope.launch {
            handler = IdleCanvasEventHandler(item)
        }
    }

    fun clear() = scope.launch {
        commands.clear()
    }

    fun copy(commands: Commands): CanvasState {
        val state = CanvasState(commands)
        state.handler = handler

        return state
    }
}

@Composable
fun rememberSchemaState(commands: Commands): CanvasState {
    var s by remember(commands) { mutableStateOf(CanvasState(commands)) }

    LaunchedEffect(commands.list.toList()) {
        s = s.copy(commands = commands)
    }

    return s
}

sealed interface CanvasActions {
    data class FocusItem(val hitBox: HitBox) : CanvasActions
    data class BlurItem(val position: Offset) : CanvasActions
    data class AddPoint(val position: Offset) : CanvasActions
}