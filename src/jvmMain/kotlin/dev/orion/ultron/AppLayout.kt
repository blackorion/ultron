package dev.orion.ultron

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
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
                    .fillMaxSize()
                    .weight(20f)
            ) {
                Text(text = "Команды:")
                CommandsList(commands)
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

    LaunchedEffect(commands.selected) {
        commands.getSelected()?.let {
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
