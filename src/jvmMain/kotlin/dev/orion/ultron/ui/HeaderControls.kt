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
import dev.orion.ultron.domain.CommandsList
import dev.orion.ultron.ui.config.ApplicationConfig

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
        ApplicationConfig.current.let { config ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ConnectionButton(arduino = arduino,
                    enabled = config.port != null,
                    onConnect = { arduino.connect(config.port!!, config.freq.toInt()) })

                IconButton(enabled = arduino.status.isConnected, onClick = { arduino.runCommands(commands) }) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow, contentDescription = "Запустить"
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TerminalButton(arduino)
                ApplicationMenuButton()
            }
        }
    }
}

@Composable
fun ConnectionButton(arduino: Arduino, enabled: Boolean, onConnect: (Int) -> Unit) {
    ApplicationConfig.current.let { config ->
        FilledTonalIconToggleButton(
            enabled = enabled,
            checked = arduino.status.isConnected,
            onCheckedChange = {
                if (it) onConnect(config.freq.toInt()) else arduino.disconnect()
            },
        ) {
            if (arduino.status.isConnected) Icon(Icons.Filled.LinkOff, contentDescription = "Отключить")
            else Icon(Icons.Filled.Link, contentDescription = "Подключить")
        }
    }
}

@Composable
fun TerminalButton(arduino: Arduino) {
    var open by remember { mutableStateOf(false) }

    IconButton(enabled = arduino.status.isConnected, onClick = { open = true }) {
        Icon(
            imageVector = Icons.Default.Terminal,
            contentDescription = "терминал",
        )
    }

    if (open) Window(onCloseRequest = {
        open = false
    }) {
        ArduinoTerminal(arduino)
    }
}

@Composable
fun ApplicationMenuButton() {
    ApplicationConfig.current.let { config ->
        IconToggleButton(
            checked = config.isOpen,
            onCheckedChange = { config.toggle(it) }
        ) {
            Icon(
                imageVector = if (config.isOpen) Icons.Default.ArrowCircleRight else Icons.Default.ArrowCircleLeft,
                contentDescription = if (config.isOpen) "Скрыть" else "Показать",
            )
        }
    }
}

fun Arduino.runCommands(commands: CommandsList) {
    commands.list.joinToString(separator = ";") { it.toString() }.let { sendMessage("$it;") }
}