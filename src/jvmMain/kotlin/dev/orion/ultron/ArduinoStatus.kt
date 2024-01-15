package dev.orion.ultron

data class ArduinoStatus(val isConnected: Boolean = false, val port: String? = null) {
    val canConnect: Boolean
        get() = !isConnected && port != null
}
