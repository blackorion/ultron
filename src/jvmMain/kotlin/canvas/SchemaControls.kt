package canvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.launch

@Composable
fun MenuItem(icon: ImageVector, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange

    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = if (checked) MaterialTheme.colors.secondary else Color.Black,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
    }
}

@Composable
fun SchemaControls(
    schemaState: SchemaState,
    mouseDetails: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row {
            MenuItem(
                icon = Icons.Default.Edit,
                checked = schemaState.isHandledBy(DrawCanvasEventHandler),
                onCheckedChange = {
                    scope.launch {
                        if (it)
                            schemaState.switchTo(DrawCanvasEventHandler)
                        else
                            schemaState.resetHandler()
                    }

                }
            )
            MenuItem(
                icon = Icons.Default.Clear,
                checked = false,
                onCheckedChange = { scope.launch { schemaState.clear() } }
            )
        }

        mouseDetails()
    }
}