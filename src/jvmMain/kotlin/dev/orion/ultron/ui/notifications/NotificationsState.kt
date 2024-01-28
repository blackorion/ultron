package dev.orion.ultron.ui.notifications

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import java.util.*
import kotlin.concurrent.schedule

data class Notification(val message: String)

class NotificationsState {
    val list = mutableStateListOf<Notification>()

    fun add(notification: Notification, delay: Long = 2000) {
        list.add(notification)

        if (delay == 0L) return

        Timer().schedule(delay) {
            list.remove(notification)
        }
    }

    fun add(message: String, delay: Long = 2000) = add(Notification(message), delay)
}

val Notifications = compositionLocalOf<NotificationsState> { error("No notifications") }