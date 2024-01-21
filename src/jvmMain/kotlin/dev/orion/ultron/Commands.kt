package dev.orion.ultron

import androidx.compose.runtime.*
import dev.orion.ultron.canvas.Shape
import dev.orion.ultron.notifications.Notifications
import dev.orion.ultron.notifications.NotificationsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Commands(private val notifications: NotificationsState) {

    val list: MutableList<Shape> = mutableStateListOf()
    var selectedIndex by mutableStateOf<Int?>(null)
    private val scope = CoroutineScope(Dispatchers.Default)

    fun selected(): Shape? {
        if (list.isEmpty()) return null

        return selectedIndex?.let { list[it] }
    }

    fun add(point: Shape) {
        list.add(point)
    }

    fun select(index: Int) {
        if (index < 0 || index >= list.size || index == selectedIndex) return

        selectedIndex = index
    }

    fun clear() = list.clear()

}

@Composable
fun rememberCommands(): Commands {
    val notifications = Notifications.current

    return remember { Commands(notifications) }
}