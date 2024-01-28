package dev.orion.ultron.ui.config

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

class ApplicationConfigState(menuIsOpen: Boolean = false, initialFreq: String = "9600", initialPort: String? = null) {
    var isOpen by mutableStateOf(menuIsOpen)
    var port by mutableStateOf(initialPort)
    var freq by mutableStateOf(initialFreq)

    fun toggle(state: Boolean) {
        isOpen = state
    }

    val canConnect: Boolean
        get() = port != null
}

val ApplicationConfig = staticCompositionLocalOf<ApplicationConfigState> {
    error("No ApplicationConfig provided")
}