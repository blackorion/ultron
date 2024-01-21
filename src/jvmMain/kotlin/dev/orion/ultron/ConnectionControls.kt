package dev.orion.ultron

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import dev.orion.ultron.canvas.SerialPortSelector

@Composable
fun ConnectionControls(
    arduino: Arduino, commands: Commands,
) {
    var port by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SerialPortSelector(onChange = {
            port = it
        }, arduino.status.isConnected)

        if (port != null)
            ConnectionButton(arduino, onConnect = { freq ->
                arduino.connect(port!!, freq)
            })

        TerminalButton(arduino)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionButton(arduino: Arduino, onConnect: (Int) -> Unit) {
    var freq by remember { mutableStateOf(9600) }

    if (arduino.status.isConnected) {
        Button(onClick = {
            arduino.disconnect()
        }) {
            Text("отключить")
        }
    } else {
        TextButton(onClick = { onConnect(freq) }) {
            Text("подключить")
        }
        TextField(
            value = freq.toString(),
            onValueChange = { freq = it.toInt() },
            placeholder = { Text("частота") },
        )
    }
}

@Composable
fun TerminalButton(arduino: Arduino) {
    var open by remember { mutableStateOf(false) }

    if (arduino.status.isConnected)
        TextButton(onClick = {
            open = true
        }) {
            Text("терминал")
        }

    if (open)
        Window(onCloseRequest = {
            open = false
        }) {
            ArduinoTerminal(arduino)
        }
}