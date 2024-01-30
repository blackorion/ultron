package dev.orion.ultron.ui.sidebar

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class SidebarState(val isInitiallyOpen: Boolean = false) {
    var isExpanded by mutableStateOf(isInitiallyOpen)
}

var Sidebar = compositionLocalOf<SidebarState> {
    error("No sidebar state provided")
}