package dev.orion.ultron

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import dev.orion.ultron.canvas.Shape
import dev.orion.ultron.notifications.Notifications
import dev.orion.ultron.notifications.NotificationsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Commands(private val notifications: NotificationsState) {

    val list: MutableList<Shape> = mutableStateListOf()
    private val scope = CoroutineScope(Dispatchers.Default)

    fun add(point: Shape) {
        list.add(point)
    }

    fun clear() = list.clear()

}

@Composable
fun rememberCommands(): Commands {
    val notifications = Notifications.current

    return remember { Commands(notifications) }
}