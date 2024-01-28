package dev.orion.ultron

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.orion.ultron.ui.AppLayout
import dev.orion.ultron.ui.config.ApplicationConfigProvider
import dev.orion.ultron.ui.notifications.NotificationsContainer
import dev.orion.ultron.ui.notifications.NotificationsProvider
import java.util.prefs.Preferences

fun main() {
    val preferences = Preferences.userRoot().node("ultron")

    application {
        Window(
            title = "Ultron",
            onCloseRequest = ::exitApplication,
        ) {
            MaterialTheme(
                colorScheme = lightColorScheme(),
                typography = MaterialTheme.typography,
                shapes = MaterialTheme.shapes,
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    ApplicationConfigProvider(preferences) {
                        NotificationsProvider {
                            AppLayout()
                            NotificationsContainer()
                        }
                    }
                }
            }
        }
    }
}

