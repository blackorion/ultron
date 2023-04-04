package canvas

import Point
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

@Composable
fun SchemaDrawer(points: SnapshotStateList<Point>) {
    var mousePosition by remember { mutableStateOf(Offset.Zero) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CanvasBoard(
            points = points,
            onClick = { points.add(Point(mousePosition)) },
            onMove = { mousePosition = it }
        )
        SchemaControls(mousePosition)
    }
}

