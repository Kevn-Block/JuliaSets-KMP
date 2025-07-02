package io.sparkedember.juliaset.settings.types

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composeunstyled.Thumb
import com.composeunstyled.ToggleSwitch
import io.sparkedember.juliaset.settings.components.BaseRowSetting
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToggleSetting(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    isToggled: Boolean,
    defaultValue: String,
    onToggleChanged: (isEnabled: Boolean) -> Unit
) {
    BaseRowSetting(
        modifier = modifier,
        title = title,
        description = description,
        defaultValue = defaultValue
    ) {
        Toggle(
            isToggled = isToggled,
            onToggleChanged = onToggleChanged
        )
    }
}

@Composable
private fun Toggle(
    isToggled: Boolean,
    onToggleChanged: (isEnabled: Boolean) -> Unit
) {
    var toggled by remember(isToggled) { mutableStateOf(isToggled) }
    val animatedColor by animateColorAsState(
        if (toggled) Color(0xFF2196F3) else Color(0xFFE0E0E0)
    )
    ToggleSwitch(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    toggled = toggled.not()
                    onToggleChanged(toggled)
                }
            ),
        toggled = toggled,
        shape = RoundedCornerShape(100),
        backgroundColor = animatedColor,
        contentPadding = PaddingValues(4.dp),
    ) {
        Thumb(
            shape = CircleShape,
            color = Color.White,
            modifier = Modifier.shadow(elevation = 4.dp, CircleShape),
        )
    }
}


@Composable
@Preview
fun ToggleSetting_Preview() {
    ToggleSetting(
        modifier = Modifier.background(Color.White),
        title = "This is neat",
        description = "This does something really neat. You should click it.",
        defaultValue = "False",
        isToggled = true,
        onToggleChanged = {}
    )
}