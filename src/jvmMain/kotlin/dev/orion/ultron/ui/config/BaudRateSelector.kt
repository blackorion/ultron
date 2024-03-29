package dev.orion.ultron.ui.config

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaudRateSelector(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit,
    enabled: Boolean = true
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onChange,
        placeholder = { Text("частота") },
        enabled = enabled
    )
}