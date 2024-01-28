@file:JvmName("CommandsListKt")

package dev.orion.ultron.domain

import androidx.compose.runtime.*

class CommandsList {

    val list = mutableStateListOf<Command>()
    var selectedIndex by mutableStateOf<Int?>(null)

    fun selected(): Command? {
        if (list.isEmpty()) return null

        return selectedIndex?.let { list[it] }
    }

    fun isSelected(command: Command): Boolean = selectedIndex?.let { list[it] == command } ?: false

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
            is CommandAction.Add -> list.add(action.command)

            is CommandAction.UpdateSelected -> selectedIndex?.let { index ->
                list[index] = action.command
            }

            is CommandAction.Delete -> {
                if (isSelected(action.command))
                    clearSelection()

                list.remove(action.command)
            }

            is CommandAction.MoveUp -> list.indexOf(action.command).let { index ->
                if (index <= 0) return@let

                val removed = list.removeAt(index)
                list.add(index - 1, removed)

                if (selectedIndex == index) selectedIndex = index - 1
            }

            is CommandAction.MoveDown -> list.indexOf(action.command).let { index ->
                if (index >= list.size - 1) return@let

                val removed = list.removeAt(index)
                list.add(index + 1, removed)

                if (selectedIndex == index) selectedIndex = index + 1
            }

            is CommandAction.Select -> select(action.command)

            is CommandAction.Selection.MoveUp ->
                selectedIndex?.let { index ->
                    if (index <= 0) return@let

                    val removed = list.removeAt(index)
                    list.add(index - 1, removed)
                    selectedIndex = index - 1
                }

            is CommandAction.Selection.MoveDown -> selectedIndex?.let { index ->
                if (index >= list.size - 1) return@let

                val removed = list.removeAt(index)
                list.add(index + 1, removed)
                selectedIndex = index + 1
            }

            is CommandAction.Selection.Remove -> selectedIndex?.let { index ->
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
fun rememberCommands(): CommandsList = remember { CommandsList() }

sealed interface CommandAction {

    sealed interface Selection : CommandAction {
        object MoveUp : Selection
        object MoveDown : Selection
        object Remove : Selection
    }

    data class Add(val command: Command) : CommandAction
    data class Select(val command: Command) : CommandAction
    data class MoveDown(val command: Command) : CommandAction
    data class MoveUp(val command: Command) : CommandAction
    data class Delete(val command: Command) : CommandAction
    data class UpdateSelected(val command: Command) : CommandAction
}