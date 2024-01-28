package dev.orion.ultron.ui.config

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fazecast.jSerialComm.SerialPort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerialPortSelector(enabled: Boolean = true, onChange: (String?) -> Unit) {
    val ports = SerialPort.getCommPorts()
    val items = ports.map { it.systemPortName }
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(-1) }

    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopStart)
    ) {
        TextField(
            value = ports.getOrNull(selected)?.portDescription ?: "usb порт",
            onValueChange = {},
            placeholder = { Text("usb порт") },
            enabled = enabled,
            readOnly = true,
            singleLine = true,
            trailingIcon = {
                Icon(imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "usb порт",
                    modifier = Modifier.clickable { expanded = true })
            })

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEachIndexed { ix, option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    selected = ix
                    expanded = false
                    onChange(option)
                })
            }
        }
    }
}