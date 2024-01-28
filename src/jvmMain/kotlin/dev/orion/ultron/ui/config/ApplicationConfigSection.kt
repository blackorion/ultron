package dev.orion.ultron.ui.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.domain.Arduino

@Composable
fun ApplicationConfigSection(modifier: Modifier = Modifier, arduino: Arduino) {
    val config = ApplicationConfig.current

    if (!config.isOpen) return

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        SerialPortSelector(enabled = !arduino.status.isConnected, onChange = { config.port = it })
        FrequencySelector(value = config.freq, onChange = { config.freq = it }, enabled = !arduino.status.isConnected)
    }
}
