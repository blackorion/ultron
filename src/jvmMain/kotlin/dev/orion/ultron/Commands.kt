package dev.orion.ultron

import androidx.compose.runtime.*

class Commands {

    val list = mutableStateListOf<Command>()
    var selectedIndex by mutableStateOf<Int?>(null)

    fun selected(): Command? {
        if (list.isEmpty()) return null

        return selectedIndex?.let { list[it] }
    }

    fun add(command: Command) {
        list.add(command)
    }

    fun select(index: Int) {
        if (index < 0 || index >= list.size || index == selectedIndex) return

        selectedIndex = index
    }

    fun select(command: Command) = select(list.indexOf(command))

    fun clear() = list.clear()

    fun apply(action: CommandAction) {
        when (action) {
            is CommandAction.MoveSelectionUp ->
                selectedIndex?.let { index ->
                    if (index <= 0) return@let

                    val removed = list.removeAt(index)
                    list.add(index - 1, removed)
                    selectedIndex = index - 1
                }

            is CommandAction.MoveSelectionDown -> selectedIndex?.let { index ->
                if (index >= list.size - 1) return@let

                val removed = list.removeAt(index)
                list.add(index + 1, removed)
                selectedIndex = index + 1
            }

            is CommandAction.Add -> list.add(action.command)

            is CommandAction.RemoveSelected -> selectedIndex?.let { index ->
                list.removeAt(index)
                selectedIndex = null
            }
        }
    }

    fun clearSelection() {
        selectedIndex = null
    }

}

@Composable
fun rememberCommands(): Commands = remember { Commands() }

sealed interface CommandAction {
    object MoveSelectionUp : CommandAction
    object MoveSelectionDown : CommandAction
    object RemoveSelected : CommandAction
    class Add(val command: Command) : CommandAction
}