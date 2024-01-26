package dev.orion.ultron

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.canvas.Shape

@Composable
fun CommandsList(
    items: List<Shape>,
    selectedIndex: Int,
    onItemClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        val state = rememberLazyListState()

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.fillMaxWidth(),
            state = state,
        ) {
            itemsIndexed(items) { index, command ->
                CommandItem(command, index == selectedIndex, onClick = { onItemClick(index) })
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(alignment = CenterEnd),
            adapter = rememberScrollbarAdapter(state)
        )
    }
}

@Composable
fun CommandItem(shape: Shape, selected: Boolean = false, onClick: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color =
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface
            )
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = shape.description(),
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        )
    }
}