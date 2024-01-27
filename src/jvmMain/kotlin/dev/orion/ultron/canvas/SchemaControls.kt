package dev.orion.ultron.canvas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.launch

@Composable
fun SchemaControls(modifier: Modifier = Modifier, canvasState: CanvasState) {
    val scope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        MenuItem(
            icon = Icons.Default.Add,
            checked = canvasState.isHandledBy(DrawCanvasEventHandler::class.java),
            onCheckedChange = {
                scope.launch {
                    if (it)
                        canvasState.switchTo(DrawCanvasEventHandler(canvasState))
                    else
                        canvasState.resetHandler()
                }

            }
        )
        MenuItem(
            icon = Icons.Default.Edit,
            checked = canvasState.isHandledBy(EditCanvasEventHandler::class.java),
            onCheckedChange = {
                scope.launch {
                    if (it)
                        canvasState.switchTo(EditCanvasEventHandler(canvasState))
                    else
                        canvasState.resetHandler()
                }

            }
        )
        MenuItem(
            icon = Icons.Default.Clear,
            checked = false,
            onCheckedChange = {
                scope.launch {
                    canvasState.resetHandler()
                    canvasState.clear()
                }
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
