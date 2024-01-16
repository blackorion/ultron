package dev.orion.ultron.canvas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.orion.ultron.Commands
import dev.orion.ultron.notifications.NotificationsState

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

@Preview
@Composable
fun SchemaDrawerPreview() {
    val commands = Commands(NotificationsState())
    SchemaDrawer(commands)
}