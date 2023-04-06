package dev.orion.ultron.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun NotificationsProvider(children: @Composable () -> Unit) {
    val notifications = NotificationsState()

    CompositionLocalProvider(Notifications provides notifications) {
        children()
    }
}