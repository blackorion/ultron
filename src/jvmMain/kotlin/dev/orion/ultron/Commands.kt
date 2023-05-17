package dev.orion.ultron

import androidx.compose.runtime.*
import com.fazecast.jSerialComm.SerialPort
import dev.orion.ultron.canvas.Shape
import dev.orion.ultron.notifications.Notifications
import dev.orion.ultron.notifications.NotificationsState

class RemoteDevice(port: String) {
    private val port = SerialPort.getCommPort(port)

    val isReady: Boolean get() = port.isOpen

    fun connect(): Boolean {
        return port.openPort()
    }

    fun close() {
        if (port.isOpen) port.closePort()
    }
}

class Commands(private val notifications: NotificationsState) {
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

    fun runTest() {
        if (port == null) {
            notifications.add("порт не указан")
            return
        }

        notifications.add("подключаемся к порту: $port")

        val arduino = SerialPort.getCommPort(port)
        arduino.setComPortParameters(9600, 8, 1, 0)
        arduino.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0)

        if (!arduino.openPort()) {
            notifications.add("ошибка подключения к порту")
            return
        }

        notifications.add("порт открыт")

        arduino.outputStream.use { os ->
            os.bufferedWriter().use {
                it.append("x0;").flush()
            }
        }

        arduino.inputStream.use { os ->
            os.bufferedReader().use {
                val line = it.readLine()
                notifications.add(line)
            }
        }

        arduino.closePort()
    }
}

@Composable
fun rememberCommands(): Commands {
    val notifications = Notifications.current

    return remember { Commands(notifications) }
}