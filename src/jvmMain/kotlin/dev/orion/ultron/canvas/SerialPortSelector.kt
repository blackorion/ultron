package dev.orion.ultron.canvas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fazecast.jSerialComm.SerialPort

@Composable
fun SerialPortSelector(onChange: (String?) -> Unit) {
    val ports = SerialPort.getCommPorts()
    val items = ports.map { it.systemPortName }
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(-1) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Button(onClick = { expanded = true }) {
            Text(text = ports.getOrNull(selected)?.portDescription ?: "usb порт")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { ix, option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selected = ix
                        expanded = false
                        onChange(option)
                    }
                )
            }
        }
    }
}