package dev.orion.ultron.ui.sidebar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun SidebarStateProvider(children: @Composable () -> Unit) {
    val state = remember { SidebarState() }

    CompositionLocalProvider(Sidebar provides state) {
        children()
    }
}