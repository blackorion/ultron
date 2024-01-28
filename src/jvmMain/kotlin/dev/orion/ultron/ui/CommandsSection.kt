package dev.orion.ultron.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material.icons.filled.VerticalAlignBottom
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
import dev.orion.ultron.domain.Command
import dev.orion.ultron.domain.CommandAction
import dev.orion.ultron.domain.CommandsList

@Composable
fun CommandsSection(modifier: Modifier = Modifier, commands: CommandsList) {
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

            val isValid by derivedStateOf {
                if (command.isEmpty()) true
                else Command.isValid(command)
            }

            LaunchedEffect(commands.selectedIndex) {
                commands.selected()?.let {
                    command = it.toString()
                }
            }

            Actions(
                command = command,
                onAction = { commands.apply(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )
            CommandEditor(command = command, onChange = { command = it }, isValid = isValid, onDone = {
                Command.parse(command)?.let { commands.apply(CommandAction.Add(it)) }
                command = ""
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CommandEditor(
    modifier: Modifier = Modifier,
    command: String,
    isValid: Boolean,
    onChange: (String) -> Unit,
    onDone: () -> Unit
) {
    val (focusRequester) = FocusRequester.createRefs()

    TextField(
        value = command,
        onValueChange = onChange,
        label = { Text("Команда") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusRequester.requestFocus() }),
        isError = !isValid,
        modifier = modifier
            .fillMaxWidth()
            .onKeyEvent {
                if (it.key == Key.Enter || it.key == Key.NumPadEnter) {
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row {
            IconButton(onClick = {
                onAction(CommandAction.Add(Command.ToggleZAxis(-1)))
            }) {
                Icon(
                    imageVector = Icons.Filled.VerticalAlignBottom, contentDescription = "Z-1"
                )
            }
            IconButton(onClick = {
                onAction(CommandAction.Add(Command.ToggleZAxis(1)))
            }) {
                Icon(
                    imageVector = Icons.Filled.Upgrade, contentDescription = "Z1"
                )
            }
        }
        Row {
            IconButton(onClick = {
                Command.parse(command)?.let { onAction(CommandAction.UpdateSelected(it)) }
            }) {
                Icon(
                    imageVector = Icons.Default.SyncAlt, contentDescription = "Обновить"
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