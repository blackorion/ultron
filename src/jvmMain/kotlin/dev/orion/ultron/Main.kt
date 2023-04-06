package dev.orion.ultron

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.orion.ultron.notifications.NotificationsContainer
import dev.orion.ultron.notifications.NotificationsProvider

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 1200.dp, height = 1000.dp)
    ) {
        MaterialTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                NotificationsProvider {
                    AppLayout()
                    NotificationsContainer()
                }
            }
        }
    }
}

