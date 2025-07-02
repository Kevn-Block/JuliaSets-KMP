package io.sparkedember.juliaset.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp

@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    generationTimeInMs: Double,
    onSettingsClicked: () -> Unit
) {
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
        Text(
            text = "Generated in $generationTimeInMs ms",
            color = Color.White,
            style = LocalTextStyle.current.copy(
                shadow = Shadow(Color.Black, blurRadius = 2f)
            )
        )
    }
}