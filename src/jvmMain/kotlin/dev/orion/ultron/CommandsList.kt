package dev.orion.ultron

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.orion.ultron.canvas.Shape

@Composable
fun CommandsList(commands: Commands) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        items(commands.list) { command ->
            CommandItem(command)
        }
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