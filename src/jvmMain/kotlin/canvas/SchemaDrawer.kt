package canvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun SchemaDrawer(commands: Commands) {
    val schemaState = rememberSchemaState(commands)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CanvasBoard(
            points = schemaState.shapes,
            onClick = { schemaState.onClick() },
            onMove = { schemaState.onMouseMove(it) }
        )
        SchemaControls(schemaState = schemaState, mouseDetails = {
            Row {
                Text("x: ${schemaState.mousePosition.x}")
                Text("y: ${schemaState.mousePosition.y}")
            }
        })
    }
}

