package dev.orion.ultron

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
            command.value = it.description()
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
            onItemClick = { commands.select(it) },
            modifier = Modifier.weight(1f)
        )
        CommandEditor(commands)

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = { commands.clear() }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow, contentDescription = "Запустить"
                )
            }
            IconButton(onClick = { commands.clear() }) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Добавить"
                )
            }
            IconButton(onClick = { commands.clear() }) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "Очистить"
                )
            }
        }
    }
}