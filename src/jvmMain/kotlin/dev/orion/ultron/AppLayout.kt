package dev.orion.ultron

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.canvas.SchemaDrawer

fun runCommands(arduino: Arduino, commands: Commands) {
    commands.list.joinToString(separator = ";") { it.toString() }.let { arduino.sendMessage(it) }
}

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
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 56.dp)
        ) {
            IconButton(
                enabled = arduino.status.isConnected,
                onClick = { runCommands(arduino, commands) }
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.PlayArrow,
                    contentDescription = "Запустить"
                )
            }
            ConnectionControls(
                arduino,
                commands,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            CommandsSection(
                modifier = Modifier.weight(2f)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
                commands
            )
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
                    SchemaDrawer(commands = commands)
                }
            }
        }
    }
}

