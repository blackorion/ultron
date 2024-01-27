package dev.orion.ultron

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp

@Composable
fun CommandsList(
    items: List<Command>,
    selectedIndex: Int,
    onAction: (CommandAction) -> Unit = {},
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
                CommandItem(command, index == selectedIndex, onAction = onAction)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(alignment = CenterEnd),
            adapter = rememberScrollbarAdapter(state)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommandItem(command: Command, selected: Boolean = false, onAction: (CommandAction) -> Unit = {}) {
    val hoverState = remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color =
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface
            )
            .clickable { onAction(CommandAction.Select(command)) }
            .onPointerEvent(PointerEventType.Enter) {
                hoverState.value = true
            }.onPointerEvent(PointerEventType.Exit) {
                hoverState.value = false
            }) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = command.toString(),
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier.alpha(if (hoverState.value) 1f else 0f)
        ) {
            IconButton(onClick = { onAction(CommandAction.MoveDown(command)) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Опустить"
                )
            }
            IconButton(onClick = { onAction(CommandAction.MoveUp(command)) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Поднять"
                )
            }
            IconButton(onClick = { onAction(CommandAction.Delete(command)) }) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "Удалить"
                )
            }
        }
    }
}