package dev.orion.ultron.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.orion.ultron.domain.Arduino
import dev.orion.ultron.domain.rememberCommands
import dev.orion.ultron.ui.canvas.Schema
import dev.orion.ultron.ui.config.ApplicationConfigSection

@Composable
fun AppLayout() {
    val arduino = remember { Arduino() }
    val commands = rememberCommands()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeaderControls(
            arduino = arduino,
            commands = commands,
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            CommandsSection(
                commands = commands,
                modifier = Modifier.weight(1f)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
            )
            Schema(
                commands = commands,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(400.dp)
            )
            ApplicationConfigSection(
                arduino = arduino,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .defaultMinSize(minWidth = 300.dp)
            )
        }
    }
}

