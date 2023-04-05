package canvas

import Point
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

class Commands {
    val list: MutableList<Point> = mutableStateListOf()

    fun add(point: Point) {
        list.add(point)
    }

    fun clear() {
        list.clear()
    }
}

@Composable
fun rememberCommands() = remember { Commands() }