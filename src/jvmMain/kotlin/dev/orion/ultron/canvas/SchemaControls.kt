package dev.orion.ultron.canvas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SchemaControls(modifier: Modifier = Modifier, canvasState: CanvasState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        MenuItem(
            icon = Icons.Default.Add,
            checked = canvasState.isHandledBy(DrawCanvasEventHandler::class.java),
            onCheckedChange = {
                if (it)
                    canvasState.switchTo(DrawCanvasEventHandler(canvasState))
                else
                    canvasState.resetHandler()
            }
        )
        MenuItem(
            icon = Icons.Default.Delete,
            checked = false,
            onCheckedChange = {
                canvasState.resetHandler()
                canvasState.clear()
            }
        )
    }
}

@Composable
fun MenuItem(icon: ImageVector, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = Modifier.background(
            color = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = if (checked) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
    }
}
