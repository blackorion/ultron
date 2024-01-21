package dev.orion.ultron

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.canvas.SchemaDrawer

fun runCommands(arduino: Arduino, commands: Commands) {
    commands.list.joinToString(separator = ";") { command ->
        "x${command.position.x.toInt()}y${command.position.y.toInt()}"
    }.let { arduino.sendMessage(it) }
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
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .weight(2f)
            ) {
                Text(text = "Команды:")
                Column(modifier = Modifier.weight(1f)) {
                    CommandsList(commands)
                }
                CommandEditor(commands)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandEditor(commands: Commands) {
    val command = remember { mutableStateOf("") }

    LaunchedEffect(commands.selectedIndex) {
        commands.selected()?.let {
            command.value = it.description()
        }
    }

    TextField(
        value = command.value,
        onValueChange = { command.value = it },
        label = { Text("Команда") },
        modifier = Modifier
            .fillMaxWidth()
    )
}
