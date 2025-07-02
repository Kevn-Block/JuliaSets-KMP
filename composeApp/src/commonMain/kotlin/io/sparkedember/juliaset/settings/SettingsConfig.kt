package io.sparkedember.juliaset.settings

import androidx.compose.ui.geometry.Offset
import io.sparkedember.juliaset.const.Defaults

data class SettingsConfig(
    val isAnimateConstantEnabled: Boolean = false,
    val animateConstantMs: Int = Defaults.AnimationDelay,
    val zoomRange: Float = Defaults.Zoom,
    val constant: Offset? = null,
    val iterationsUntilEscaped: Int = Defaults.IterationsUntilEscaped,
    val coloringMethodIndex: Int? = null
)