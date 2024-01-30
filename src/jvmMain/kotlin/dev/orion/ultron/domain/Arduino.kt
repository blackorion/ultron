package dev.orion.ultron.domain

import androidx.compose.runtime.*
import com.fazecast.jSerialComm.SerialPort
import dev.orion.ultron.ui.ArduinoStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.BufferedReader

@OptIn(DelicateCoroutinesApi::class)
class Arduino() {

    val canConnect: Boolean
        get() = port != null && !isConnected
    var port by mutableStateOf<String?>(null)
    var baudRate by mutableStateOf(9600)
    var isConnected by mutableStateOf(false)

    private var connection: Connection? = null

    fun connect(): ArduinoStatus {
        if (connection != null)
            throw IllegalStateException("already connected")

        if (port == null)
            throw IllegalStateException("port is not set")

        val serialPort = SerialPort.getCommPort(port).apply {
            setComPortParameters(this@Arduino.baudRate, 8, 1, 0)
            setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0)
        }

        connection = Connection.open(serialPort)
        isConnected = true

        return ArduinoStatus(true, port)
    }

    fun disconnect(): ArduinoStatus {
        connection?.close()
        connection = null
        isConnected = false

        return ArduinoStatus()
    }

    fun sendMessage(message: String) {
        connection?.send(message)
    }

    fun getMessagesFlow(): Flow<String> {
        if (connection == null)
            throw IllegalStateException("not connected")

        return connection!!.read()
    }

}

@DelicateCoroutinesApi
data class Connection(private val serialPort: SerialPort) : AutoCloseable {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val sendChannel = Channel<String>()

    init {
        scope.launch {
            val writer = serialPort.outputStream.bufferedWriter()

            for (message in sendChannel) {
                writer.append(message)
                writer.newLine()
                writer.flush()
            }

            writer.close()
        }
    }

    fun send(message: String) = scope.launch {
        if (!sendChannel.isClosedForSend)
            sendChannel.send(message.trim())
    }

    fun read() = serialPort
        .inputStream
        .bufferedReader()
        .asFlow()

    override fun close() {
        serialPort.removeDataListener()
        serialPort.closePort()
        sendChannel.close()
    }

    companion object {
        fun open(serialPort: SerialPort): Connection {
            serialPort.openPort()

            return Connection(serialPort)
        }
    }
}

@Composable
fun rememberArduino(): Arduino = remember { Arduino() }

fun BufferedReader.asFlow(): Flow<String> {
    return flow {
        while (true)
            emit(readLine())
    }
        .cancellable()
        .onCompletion {
            close()
            println("closed")
        }
        .flowOn(Dispatchers.IO)
}