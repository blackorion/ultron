package dev.orion.ultron.canvas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.orion.ultron.Commands

@Composable
fun SchemaDrawer(commands: Commands) {
    val state = rememberSchemaState(commands)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CanvasBoard(canvasState = state)
        SchemaControls(canvasState = state)
    }
}

@Preview
@Composable
fun SchemaDrawerPreview() {
    SchemaDrawer(Commands())
}