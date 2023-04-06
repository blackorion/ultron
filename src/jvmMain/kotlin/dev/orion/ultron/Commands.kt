package dev.orion.ultron

import androidx.compose.runtime.*
import com.fazecast.jSerialComm.SerialPort
import dev.orion.ultron.canvas.Shape

class RemoteDevice(port: String) {
    private val port = SerialPort.getCommPort(port)

    val isReady: Boolean get() = port.isOpen

    fun connect() {
        port.openPort()
    }

    fun close() {
        if (port.isOpen)
            port.closePort()
    }
}

class Commands {
    private var port by mutableStateOf<String?>(null)
    private var remoteConnection: RemoteDevice? = null
    val list: MutableList<Shape> = mutableStateListOf()

    val hasPort: Boolean
        @Composable get() = port !== null

    val connected: Boolean
        @Composable get() = remoteConnection?.isReady ?: false

    fun add(point: Shape) {
        list.add(point)
    }

    fun clear() = list.clear()

    fun select(it: Shape?) {}

    fun setSerialPort(port: String?) {
        this.port = port

        remoteConnection?.let {
            if (it.isReady) return

            it.close()
        }
    }

    fun connect() {
        port?.let {
            remoteConnection = RemoteDevice(it)
        }
    }
}

@Composable
fun rememberCommands() = remember { Commands() }