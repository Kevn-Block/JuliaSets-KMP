package io.sparkedember.juliaset.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.Text
import io.sparkedember.juliaset.components.OutlinedButton
import io.sparkedember.juliaset.settings.types.ColoringMethodDropdownSetting
import io.sparkedember.juliaset.settings.types.NumberSetting
import io.sparkedember.juliaset.settings.types.OffsetDropdownSetting
import io.sparkedember.juliaset.settings.types.ToggleSetting
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSheet(
    activeSettings: SettingsConfig,
    onDraftSettingsChanged: (SettingsConfig) -> Unit,
    onApply: () -> Unit,
    onResetToDefaults: () -> Unit
) {
    OptionSheetContent(
        settings = activeSettings,
        onDraftSettingsChanged = onDraftSettingsChanged,
        onApply = onApply,
        onResetToDefaults = onResetToDefaults,
    )
}

@Composable
private fun OptionSheetContent(
    settings: SettingsConfig,
    onDraftSettingsChanged: (SettingsConfig) -> Unit,
    onApply: () -> Unit,
    onResetToDefaults: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .systemBarsPadding(),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Settings",
                style = TextStyle.Default.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        item {
            ToggleSetting(
                modifier = Modifier.fillMaxWidth(),
                title = "Animate Constant",
                description = "Animates the constant to generate Julia Sets. This is very intensive.",
                defaultValue = "Off",
                isToggled = settings.isAnimateConstantEnabled,
                onToggleChanged = {
                    onDraftSettingsChanged(
                        settings.copy(isAnimateConstantEnabled = it)
                    )
                }
            )
        }
        item {
            NumberSetting(
                modifier = Modifier.fillMaxWidth(),
                title = "Animated Constant Delay",
                description = "Animates the constant between -1 and 1 in the specified ms duration",
                defaultValue = "50",
                initialNumber = settings.animateConstantMs.toString(),
                onNumberChanged = {
                    onDraftSettingsChanged(
                        settings.copy(animateConstantMs = it)
                    )
                }
            )
        }
        item {
            NumberSetting(
                modifier = Modifier.fillMaxWidth(),
                title = "Iterations Until Escape",
                description = "How many times a point will be iterated on to determine if it escapes",
                defaultValue = "300",
                initialNumber = settings.iterationsUntilEscaped.toString(),
                onNumberChanged = {
                    onDraftSettingsChanged(
                        settings.copy(iterationsUntilEscaped = it)
                    )
                }
            )
        }
        item(settings.constant?.x, settings.constant?.y) {
            OffsetDropdownSetting(
                modifier = Modifier.fillMaxWidth(),
                title = "Change Offset",
                description = "Pick from a variety of offsets to view",
                currentConstant = settings.constant,
                onDropdownItemClicked = {
                    onDraftSettingsChanged(
                        settings.copy(constant = it)
                    )
                }
            )
        }
        item {
            ColoringMethodDropdownSetting(
                modifier = Modifier.fillMaxWidth(),
                title = "Coloring Method",
                description = "Pick from a variety of different coloring options",
                currentColoringMethodIndex = settings.coloringMethodIndex,
                onDropdownItemClicked = {
                    onDraftSettingsChanged(
                        settings.copy(coloringMethodIndex = it)
                    )
                }
            )
        }
        item {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Apply",
                onClick = {
                    onApply()
                }
            )
        }
        item {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Reset to Defaults",
                onClick = {
                    onResetToDefaults()
                }
            )
        }
    }
}

@Composable
@Preview
fun SettingsSheet_Preview() {
    OptionSheetContent(
        settings = SettingsConfig(),
        onDraftSettingsChanged = {},
        onApply = {},
        onResetToDefaults = {}
    )
}