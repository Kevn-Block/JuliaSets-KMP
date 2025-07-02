package io.sparkedember.juliaset.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseRowSetting(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    defaultValue: String,
    content: @Composable (ColumnScope) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingText(
            modifier = modifier
                .weight(1f),
            title = title,
            description = description,
            defaultValue = defaultValue
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(58.dp)
        ) {
            content(this)
        }
    }
}