package dev.orion.ultron

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.canvas.SchemaDrawer

@Composable
fun AppLayout() {
    val arduino = remember { Arduino() }
    val commands = rememberCommands()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AppControls(
            arduino,
            commands,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
            ) {
                Text(text = "Команды:")
                CommandsList(commands)
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(400.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SchemaDrawer(commands)
                }
            }
        }
    }
}