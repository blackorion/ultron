package dev.orion.ultron

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ArduinoTerminal(arduino: Arduino) {
    val message = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row {
            TextField(value = message.value, onValueChange = {
                message.value = it
            }, modifier = Modifier.weight(1f).onKeyEvent {
                if (it.key != Key.Enter) return@onKeyEvent false

                arduino.sendMessage(message.value)
                message.value = ""
                true
            })

            Column(modifier = Modifier.width(200.dp)) {
                IconButton(onClick = {
                    arduino.clearMessages()
                }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "очистить",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            Text("Сообщения:")

            Box {
                val state = rememberLazyListState()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    state
                ) {
                    items(arduino.messages) { message ->
                        Text(message)
                    }
                }

                VerticalScrollbar(
                    modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
                    adapter = rememberScrollbarAdapter(state)
                )
            }
        }
    }
}