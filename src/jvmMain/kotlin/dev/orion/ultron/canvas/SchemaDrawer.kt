package dev.orion.ultron.canvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.orion.ultron.Commands

@Composable
fun SchemaDrawer(commands: Commands) {
    val schemaState = rememberSchemaState(commands)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CanvasBoard(
            shapes = schemaState.shapes,
            onClick = { schemaState.onClick() },
            onMove = { schemaState.onMouseMove(it) }
        )
        SchemaControls(canvasState = schemaState)
    }
}

