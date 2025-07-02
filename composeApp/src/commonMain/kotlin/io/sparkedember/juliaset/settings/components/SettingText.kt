package io.sparkedember.juliaset.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.Text
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SettingText(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    defaultValue: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = TextStyle.Default.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = description,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Text(
            text = "Default: $defaultValue",
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
@Preview
fun SettingText_Preview() {
    SettingText(
        modifier = Modifier.background(Color.White),
        title = "Test Title",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        defaultValue = "500"
    )
}