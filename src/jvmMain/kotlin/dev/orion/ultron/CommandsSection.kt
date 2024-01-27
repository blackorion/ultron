package dev.orion.ultron

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandEditor(commands: Commands) {
    val command = remember { mutableStateOf("") }

    LaunchedEffect(commands.selectedIndex) {
        commands.selected()?.let {
            command.value = it.toString()
        }
    }

    TextField(
        value = command.value,
        onValueChange = { command.value = it },
        label = { Text("Команда") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CommandsSection(modifier: Modifier = Modifier, commands: Commands) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier
    ) {
        Text(text = "Команды:")
        CommandsList(
            items = commands.list,
            selectedIndex = commands.selectedIndex ?: -1,
            onAction = { commands.apply(it) },
            modifier = Modifier.weight(1f)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Actions(
                commands,
                onAction = { commands.apply(it) },
                modifier = Modifier.fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )
            CommandEditor(commands)
        }
    }
}

@Composable
fun Actions(commands: Commands, onAction: (CommandAction) -> Unit, modifier: Modifier = Modifier) {

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row {
            IconButton(onClick = { commands.clear() }) {
                Icon(
                    imageVector = Icons.Default.Refresh, contentDescription = "Обновить"
                )
            }
            IconButton(onClick = { commands.clear() }) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Добавить"
                )
            }
        }
    }
}