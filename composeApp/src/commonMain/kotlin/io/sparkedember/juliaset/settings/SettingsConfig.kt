package io.sparkedember.juliaset.settings

import io.sparkedember.juliaset.const.Defaults

data class SettingsConfig(
    val isGPURenderingOn: Boolean = true,
    val isAnimateConstantEnabled: Boolean = false,
    val animateConstantMs: Int = Defaults.AnimationDelay,
    val zoomRange: Float = Defaults.Zoom,
    val constantIndex: Int? = null,
    val iterationsUntilEscaped: Int = Defaults.IterationsUntilEscaped,
    val coloringMethodIndex: Int? = null
)