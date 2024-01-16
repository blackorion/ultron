package dev.orion.ultron

import androidx.compose.runtime.*
import dev.orion.ultron.canvas.Shape
import dev.orion.ultron.notifications.Notifications
import dev.orion.ultron.notifications.NotificationsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Commands(private val notifications: NotificationsState) {

    val selected by mutableStateOf<Int?>(null)
    val list: MutableList<Shape> = mutableStateListOf()
    private val scope = CoroutineScope(Dispatchers.Default)

    fun add(point: Shape) {
        list.add(point)
    }

    fun clear() = list.clear()

    fun getSelected(): Shape? = selected?.let { ix ->
        return list[ix]
    }

}

@Composable
fun rememberCommands(): Commands {
    val notifications = Notifications.current

    return remember { Commands(notifications) }
}