package dev.orion.ultron.canvas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.Commands

@Composable
fun SchemaDrawer(modifier: Modifier = Modifier, commands: Commands) {
    val state = rememberSchemaState(commands)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        CanvasBoard(modifier = Modifier.weight(1f), canvasState = state)

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth(),
        ) {
            SchemaControls(canvasState = state)
        }
    }
}

@Preview
@Composable
fun SchemaDrawerPreview() {
    SchemaDrawer(commands = Commands())
}