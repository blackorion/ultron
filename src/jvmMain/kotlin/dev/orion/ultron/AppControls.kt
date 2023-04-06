package dev.orion.ultron

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.canvas.SerialPortSelector
import dev.orion.ultron.notifications.Notifications

@Composable
fun AppControls(commands: Commands) {
    val n = Notifications.current

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SerialPortSelector(onChange = {
            commands.setSerialPort(it)
        })

        IconButton(onClick = {
            commands.runTest()
        }) {
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }

        if (commands.connected) {
            Button(onClick = {
                commands.connect()
            }) {
                Text("отключить")
            }
        } else if (commands.hasPort)
            Button(onClick = {
                commands.connect()
            }) {
                Text("подключить")
            }
    }
}