package dev.orion.ultron.ui.serialport

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import java.util.prefs.Preferences

@Composable
fun SerialPortConfigProvider(children: @Composable () -> Unit) {
    val preferences = remember{ Preferences.userRoot().node("ultron") }
    val config = remember {
        SerialPortConfigState(
            initialPort = preferences.get("port", null),
            initialBaudRate = preferences.get("baudRate", "9600")
        )
    }

    DisposableEffect(config) {
        onDispose {
            preferences.put("port", config.port)
            preferences.put("baudRate", config.baudRate)
        }
    }

    CompositionLocalProvider(SerialPortConfig provides config) {
        children()
    }
}