package dev.orion.ultron.canvas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
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
                DropdownMenuItem(onClick = {
                    selected = ix
                    expanded = false
                    onChange(option)
                }) {
                    Text(option)
                }
            }
        }
    }
}