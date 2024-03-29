package dev.orion.ultron.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.domain.rememberArduino
import dev.orion.ultron.domain.rememberCommands
import dev.orion.ultron.ui.serialport.SerialPortConfig
import dev.orion.ultron.ui.canvas.Schema
import dev.orion.ultron.ui.notifications.Notifications
import dev.orion.ultron.ui.config.ApplicationConfigSection
import dev.orion.ultron.ui.sidebar.Sidebar

@Composable
fun AppLayout() {
    val serialPortConfig = SerialPortConfig.current
    val arduino = rememberArduino()
    val commands = rememberCommands()

    LaunchedEffect(serialPortConfig.port, serialPortConfig.baudRate) {
        arduino.port = serialPortConfig.port
        arduino.baudRate = serialPortConfig.baudRate.toInt()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeaderControls(
            arduino = arduino,
            commands = commands,
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            val notifications = Notifications.current

            CommandsSection(
                commands = commands,
                onRun = {
                    arduino.runCommands(listOf(it))
                    notifications.add("'$it' выполнена")
                },
                modifier = Modifier.weight(1f)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
            )
            Schema(
                commands = commands,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(400.dp)
            )
            Sidebar {
                ApplicationConfigSection(
                    arduino = arduino,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)
                        .defaultMinSize(minWidth = 300.dp)
                )
            }
        }
    }
}

