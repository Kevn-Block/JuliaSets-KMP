package io.sparkedember.juliaset.const

import androidx.compose.ui.geometry.Offset

val DemoJuliaSets = listOf(
    JuliaSet(
        name = "The Classic Dendrite",
        offset = Offset(0.0f, 1.0f)
    ),
    JuliaSet(
        name = "Douady's Rabbit",
        offset = Offset(-0.122f, 0.745f)
    ),
    JuliaSet(
        name = "A Dramatic Double Spiral",
        offset = Offset(-0.8f, 0.156f)
    ),
    JuliaSet(
        name = "The San Marco Dragon",
        offset = Offset(-0.75f, 0.11f)
    ),
    JuliaSet(
        name = "Cauliflower Island",
        offset = Offset(0.285f, 0.01f)
    ),
    JuliaSet(
        name = "Disconnected Fatou Dust",
        offset = Offset(0.3f, 0.5f)
    ),
    JuliaSet(
        name = "The Needle",
        offset = Offset(-1.755f, 0.0f)
    )
)

data class JuliaSet(val name: String, val offset: Offset)