package io.sparkedember.juliaset.settings.types

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.DropdownMenu
import com.composeunstyled.DropdownMenuPanel
import com.composeunstyled.Icon
import com.composeunstyled.Text
import io.sparkedember.juliaset.components.OutlinedButton
import io.sparkedember.juliaset.const.ColoringMethods
import io.sparkedember.juliaset.const.Defaults
import io.sparkedember.juliaset.settings.components.BaseRowSetting
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ColoringMethodDropdownSetting(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    currentColoringMethodIndex: Int?,
    onDropdownItemClicked: (index: Int) -> Unit
) {
    BaseRowSetting(
        modifier = modifier,
        title = title,
        description = description,
        defaultValue = ColoringMethods[Defaults.ColoringMethodIndex].name()
    ) {
        Dropdown(
            currentIndex = currentColoringMethodIndex,
            onDropdownItemClicked = onDropdownItemClicked
        )
    }
}

@Composable
private fun Dropdown(
    currentIndex: Int?,
    onDropdownItemClicked: (index: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    DropdownMenu(
        onExpandRequest = { expanded = true }
    ) {
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Set",
            fontSize = 14.sp,
            onClick = { expanded = true }
        )
        DropdownMenuPanel(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            backgroundColor = Color.White,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(vertical = 4.dp)
                .width(240.dp)
                .shadow(4.dp, RoundedCornerShape(8.dp))
        ) {
            ColoringMethods.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .clickable {
                            expanded = false
                            onDropdownItemClicked(index)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = item.name(),
                    )
                    if (currentIndex == index) {
                        Icon(
                            modifier = Modifier.padding(start = 8.dp),
                            imageVector = Icons.Default.Check,
                            tint = Color.Green,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun ColoringMethodDropdownSetting_Preview() {
    ColoringMethodDropdownSetting(
        modifier = Modifier.background(Color.White),
        title = "This is neat",
        description = "This does something really neat. You should click it.",
        currentColoringMethodIndex = 0,
        onDropdownItemClicked = {}
    )
}