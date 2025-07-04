package io.sparkedember.juliaset.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp

@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    isUsingGPURendering: Boolean,
    generationTimeInMs: Double,
    onSettingsClicked: () -> Unit
) {
    var frameTime by remember { mutableStateOf(0L) }
    LaunchedEffect(Unit) {
        var lastFrame = 0L
        while (true) {
            withFrameNanos {
                frameTime = (it - lastFrame) / 1_000_000
                lastFrame = it
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(4.dp)
    ) {
        Icon(
            modifier = Modifier
                .clickable { onSettingsClicked() },
            imageVector = Icons.Default.Settings,
            tint = Color.White,
            contentDescription = null
        )
        Spacer(Modifier.weight(1f))
        Column {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = if (isUsingGPURendering) {
                    "Frametime is $frameTime ms"
                } else {
                    "Generated in $generationTimeInMs ms"
                },
                color = Color.White,
                style = LocalTextStyle.current.copy(
                    shadow = Shadow(Color.Black, blurRadius = 2f)
                )
            )
            Text(
                text = "Using ${"GPU".takeIf { isUsingGPURendering } ?: "CPU"} Rendering",
                color = Color.White,
                style = LocalTextStyle.current.copy(
                    shadow = Shadow(Color.Black, blurRadius = 2f)
                )
            )
        }
    }
}