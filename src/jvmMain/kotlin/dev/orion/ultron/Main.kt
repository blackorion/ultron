package dev.orion.ultron

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.orion.ultron.AppLayout

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState( width = 1200.dp, height = 1000.dp )
    ) {
        MaterialTheme {
            AppLayout()
        }
    }
}

