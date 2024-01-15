package dev.orion.ultron

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortDataListener
import com.fazecast.jSerialComm.SerialPortEvent

class Arduino {
    val status = mutableStateOf(ArduinoStatus())
    val messages = mutableStateListOf<String>()
    private var arduino: SerialPort? = null

    fun connect(port: String): Boolean {
        if (isConnected())
            throw IllegalStateException("already connected")

        arduino = SerialPort.getCommPort(port)

        return arduino?.let {
            it.setComPortParameters(9600, 8, 1, 0)
            it.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0)

            it.addDataListener(object : SerialPortDataListener {
                override fun getListeningEvents(): Int {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE
                }

                override fun serialEvent(event: SerialPortEvent) {
                    if (event.eventType != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return

                    var message = ""

                    while (it.bytesAvailable() > 0) {
                        it.inputStream.bufferedReader().use { reader ->
                            message += reader.readLine()
                        }
                    }

                    messages.add(message)
                }
            })

            val connection = it.openPort()

            if (connection)
                status.value = ArduinoStatus(isConnected = true, port)

            return connection
        } ?: false
    }

    fun isConnected(): Boolean {
        return arduino?.isOpen ?: false
    }

    fun disconnect() {
        arduino?.closePort()
        status.value = ArduinoStatus(false)
    }

    fun sendMessage(message: String) {
        arduino?.let {
            it.outputStream.use { os ->
                os.bufferedWriter().use { writer ->
                    writer.append(message).flush()
                }
            }
        }
    }

    fun clearMessages() {
        messages.clear()
    }
}