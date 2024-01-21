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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppControls(
    arduino: Arduino, commands: Commands,
) {
    var freq by remember { mutableStateOf<Int>(9600) }
    val port = remember { mutableStateOf<String?>(null) }
    var open by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SerialPortSelector(onChange = {
            port.value = it
        })

        if (arduino.status.value.isConnected) {
            Button(onClick = {
                arduino.disconnect()
            }) {
                Text("отключить")
            }
        } else if (port.value != null && !arduino.status.value.isConnected) {
            TextField(
                value = freq.toString(),
                onValueChange = { freq = it.toInt() },
                placeholder = { Text("частота") },
            )
            TextButton(onClick = {
                arduino.connect(port.value!!, freq)
            }) {
                Text("подключить")
            }
        }

        if (arduino.status.value.isConnected)
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
}

