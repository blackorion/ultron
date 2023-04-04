import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import canvas.SchemaDrawer

@Composable
@Preview
fun AppLayout() {
    val points: SnapshotStateList<Point> = remember { mutableStateListOf() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f)
                .padding(16.dp),
        ) {
            Text(text = "Команды:")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray)
                    .padding(4.dp)
            ) {
                items(points) { point ->
                    Text(
                        point.description(), modifier = Modifier
                            .padding(PaddingValues(vertical = 4.dp))
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxHeight()
                .width(400.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SchemaDrawer(points)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AppControls(points)
            }
        }
    }
}