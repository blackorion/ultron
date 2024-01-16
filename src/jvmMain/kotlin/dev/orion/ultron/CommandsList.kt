package dev.orion.ultron

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.canvas.Shape

@Composable
fun CommandsList(commands: Commands) {
    Box(modifier = Modifier.fillMaxWidth()) {
        val state = rememberLazyListState()

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
            state = state,
        ) {
            items(commands.list) { command ->
                CommandItem(command)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(alignment = CenterEnd),
            adapter = rememberScrollbarAdapter(state)
        )
    }
}

@Composable
fun CommandItem(shape: Shape) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = shape.description())
    }
}