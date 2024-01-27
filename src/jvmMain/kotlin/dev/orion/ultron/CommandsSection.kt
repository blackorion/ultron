package dev.orion.ultron

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

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
            var command by remember { mutableStateOf("") }

            LaunchedEffect(commands.selectedIndex) {
                commands.selected()?.let {
                    command = it.toString()
                }
            }

            Actions(
                command = command,
                onAction = { commands.apply(it) },
                modifier = Modifier.fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )
            CommandEditor(command = command, onChange = { command = it }, onDone = {
                Command.parse(command)?.let { commands.apply(CommandAction.Add(it)) }
                command = ""
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CommandEditor(modifier: Modifier = Modifier, command: String, onChange: (String) -> Unit, onDone: () -> Unit) {
    val (focusRequester) = FocusRequester.createRefs()

    TextField(
        value = command,
        onValueChange = onChange,
        label = { Text("Команда") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusRequester.requestFocus() }),
        modifier = modifier
            .fillMaxWidth()
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onDone()
                    true
                } else {
                    false
                }
            }
    )
}

@Composable
fun Actions(command: String, onAction: (CommandAction) -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        Row {
            IconButton(onClick = {
                Command.parse(command)?.let { onAction(CommandAction.UpdateSelected(it)) }
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh, contentDescription = "Обновить"
                )
            }
            IconButton(onClick = {
                Command.parse(command)?.let { onAction(CommandAction.Add(it)) }
            }) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Добавить"
                )
            }
        }
    }
}