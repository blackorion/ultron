package dev.orion.ultron.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import dev.orion.ultron.domain.Arduino
import dev.orion.ultron.domain.Command
import dev.orion.ultron.domain.CommandsList
import dev.orion.ultron.ui.sidebar.Sidebar

@Composable
fun HeaderControls(
    modifier: Modifier = Modifier,
    arduino: Arduino,
    commands: CommandsList,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ConnectionButton(
                arduino = arduino,
                enabled = arduino.canConnect,
            )

            IconButton(
                enabled = arduino.isConnected,
                onClick = { arduino.runCommands(commands.list.toList()) }
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow, contentDescription = "Запустить"
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TerminalButton(
                arduino = arduino,
            )
            ApplicationMenuButton()
        }
    }
}

@Composable
fun ConnectionButton(arduino: Arduino, enabled: Boolean) {
    FilledTonalIconToggleButton(
        enabled = enabled,
        checked = arduino.isConnected,
        onCheckedChange = {
            if (it) arduino.connect()
            else arduino.disconnect()
        },
    ) {
        if (arduino.isConnected) Icon(Icons.Filled.LinkOff, contentDescription = "Отключить")
        else Icon(Icons.Filled.Link, contentDescription = "Подключить")
    }
}

@Composable
fun TerminalButton(
    arduino: Arduino,
) {
    var open by remember { mutableStateOf(false) }

    IconButton(enabled = arduino.isConnected, onClick = { open = true }) {
        Icon(
            imageVector = Icons.Default.Terminal,
            contentDescription = "терминал",
        )
    }

    if (open) Window(onCloseRequest = {
        open = false
    }) {
        SerialPortTerminal(arduino)
    }
}

@Composable
fun ApplicationMenuButton() {
    Sidebar.current.let { sidebar ->
        IconToggleButton(
            checked = sidebar.isExpanded,
            onCheckedChange = { sidebar.isExpanded = it }
        ) {
            Icon(
                imageVector = if (sidebar.isExpanded) Icons.Default.ArrowCircleRight else Icons.Default.ArrowCircleLeft,
                contentDescription = if (sidebar.isExpanded) "Скрыть" else "Показать",
            )
        }
    }
}

fun Arduino.runCommands(commands: List<Command>) {
    sendMessage("${commands.joinToString(separator = ";") { it.toString() }};")
}