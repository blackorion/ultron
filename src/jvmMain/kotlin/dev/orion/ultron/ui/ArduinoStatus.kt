package dev.orion.ultron.ui

data class ArduinoStatus(
    val isConnected: Boolean = false,
    val port: String? = null,
) {
}
