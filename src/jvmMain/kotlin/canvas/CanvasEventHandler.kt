package canvas

import Point
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector

sealed class CanvasEventHandler {
    abstract val icon: ImageVector

    abstract fun handleClick(offset: Offset, commands: Commands)
}

object IdleCanvasEventHandler : CanvasEventHandler() {
    override val icon = Icons.Filled.Edit

    override fun handleClick(offset: Offset, commands: Commands) {}
}

object DrawCanvasEventHandler : CanvasEventHandler() {
    override val icon = Icons.Outlined.Edit

    override fun handleClick(offset: Offset, commands: Commands) {
        commands.add(Point(offset))
    }
}

object ResetCanvasEventHandler : CanvasEventHandler() {
    override val icon = Icons.Default.Clear

    override fun handleClick(offset: Offset, commands: Commands) {}
}