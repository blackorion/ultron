package dev.orion.ultron.ui.sidebar

import androidx.compose.runtime.Composable

@Composable
fun Sidebar(children: @Composable () -> Unit) {
    val sidebar = Sidebar.current

    if (sidebar.isExpanded)
        children()
}