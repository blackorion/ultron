package dev.orion.ultron.ui.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import java.util.prefs.Preferences

@Composable
fun ApplicationConfigProvider(preferences: Preferences, children: @Composable () -> Unit) {
    val state = ApplicationConfigState(
        initialPort = preferences.get("port", null),
        initialFreq = preferences.get("freq", "9600"),
    )

    DisposableEffect(state) {
        onDispose {
            preferences.put("port", state.port)
            preferences.put("freq", state.freq)
        }
    }

    CompositionLocalProvider(ApplicationConfig provides state) {
        children()
    }
}