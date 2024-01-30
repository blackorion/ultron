package dev.orion.ultron.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.orion.ultron.domain.Arduino
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SerialPortTerminal(
    arduino: Arduino,
) {
    val scope = rememberCoroutineScope()
    val message = remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<String>() }

    DisposableEffect(arduino) {
        val job = scope.launch {
            arduino.getMessagesFlow().collectLatest {
                messages.add(it)
            }
        }

        onDispose { job.cancel() }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row {
            TextField(
                value = message.value,
                onValueChange = { message.value = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.weight(1f).onKeyEvent {
                    if (it.key != Key.Enter && it.key != Key.NumPadEnter) return@onKeyEvent false

                    if (message.value.isBlank()) return@onKeyEvent true

                    arduino.sendMessage(message.value)
                    message.value = ""
                    true
                })

            Column(modifier = Modifier.width(200.dp)) {
                IconButton(onClick = { messages.clear() }) {
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
                    items(messages) { message ->
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