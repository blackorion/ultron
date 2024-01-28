package dev.orion.ultron.ui.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun ApplicationConfigProvider(children: @Composable () -> Unit) {
    val state = ApplicationConfigState()

    CompositionLocalProvider(ApplicationConfig provides state) {
        children()
    }
}