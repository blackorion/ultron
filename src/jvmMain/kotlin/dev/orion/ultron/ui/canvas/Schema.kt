package dev.orion.ultron.ui.canvas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.domain.CommandsList

@Composable
fun Schema(modifier: Modifier = Modifier, commands: CommandsList) {
    val state = rememberSchemaState(commands)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        CanvasBoard(
            canvasState = state,
            modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.background)
        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface),
        ) {
            SchemaControls(canvasState = state)
        }
    }
}

@Preview
@Composable
fun SchemaDrawerPreview() {
    Schema(commands = CommandsList())
}