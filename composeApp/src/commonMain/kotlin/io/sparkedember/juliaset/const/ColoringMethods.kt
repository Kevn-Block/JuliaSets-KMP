package io.sparkedember.juliaset.const

import androidx.compose.ui.graphics.Color
import kotlin.math.log

sealed class ColoringMethod {

    abstract fun toColor(zx: Float, zy: Float, iteration: Int): Color
    abstract fun name(): String

    object Default: ColoringMethod() {
        override fun toColor(zx: Float, zy: Float, iteration: Int): Color {
            val hue = (iteration * 10f) % 360f
            return Color.hsv(hue, 0.9f, 1f)
        }

        override fun name(): String =
            "Default"
    }

    object NoBanding: ColoringMethod() {
        override fun toColor(zx: Float, zy: Float, iteration: Int): Color {
            val logZn = log(zx * zx + zy * zy, 2f)
            val nu = log(logZn / 2f, 2f)
            val smoothValue = iteration + 1 - nu

            val hue = (smoothValue * 15f) % 360f
            return Color.hsv(hue, 0.9f, 1f)
        }

        override fun name(): String =
            "No Color Banding"
    }

    object Palette: ColoringMethod() {
        private val colors = listOf(
            Color(0xFF07040A),
            Color(0xFF2C0B0E),
            Color(0xFF6A0C0F),
            Color.hsv(35f, 0.9f, 0.9f),
            Color.hsv(55f, 0.9f, 1f)
        )

        override fun toColor(zx: Float, zy: Float, iteration: Int): Color {
            return colors[iteration % colors.size]
        }

        override fun name(): String =
            "Palette"
    }

    object BlackAndWhite: ColoringMethod() {
        override fun toColor(zx: Float, zy: Float, iteration: Int): Color =
            if (iteration % 2 == 0) Color.White else Color.Black

        override fun name(): String =
            "Black and White"
    }
}

val ColoringMethods = listOf(
    ColoringMethod.Default,
    ColoringMethod.NoBanding,
    ColoringMethod.BlackAndWhite,
    ColoringMethod.Palette
)
