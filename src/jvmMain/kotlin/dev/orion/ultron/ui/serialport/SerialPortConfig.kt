package dev.orion.ultron.ui.serialport

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SerialPortConfigState(
    initialPort: String? = null,
    initialBaudRate: String = "9600"
) {
    var port by mutableStateOf(initialPort)
    var baudRate by mutableStateOf(initialBaudRate)
}

val SerialPortConfig = compositionLocalOf<SerialPortConfigState> {
    error("no serial port configuration found")
}