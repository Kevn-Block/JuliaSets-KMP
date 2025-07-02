package io.sparkedember.juliaset.geometry

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.sparkedember.juliaset.const.ColoringMethods
import io.sparkedember.juliaset.const.Defaults
import io.sparkedember.juliaset.system.getAvailableProcessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.math.ceil

object JuliaSetGeneration {
    suspend fun generateByteArray(canvasSize: Size, coroutineScope: CoroutineScope, options: GenerationOptions): ByteArray {
        val (wFloat, hFloat) = canvasSize
        val (wInt, hInt) = wFloat.toInt() to hFloat.toInt()
        val (centerX, centerY) = options.center
        val iterationsUntilEscape = options.iterationsUntilEscaped
        val aspectRatio = canvasSize.aspectRatio
        val constant = options.constant
        val zoomRangeY = options.zoomRange

        val expectedArraySize = wInt * hInt * 4
        val byteArray = ByteArray(expectedArraySize)

        val zoomRangeX = zoomRangeY * aspectRatio
        val amountOfJobsToLaunch = getAvailableProcessors()
        val batchAmount = ceil(hInt / amountOfJobsToLaunch.toFloat()).toInt()

        val coloringMethod = ColoringMethods[options.coloringMethodIndex]

        val jobs =
            (0 until amountOfJobsToLaunch).map { jobNumber ->
                coroutineScope.launch(Dispatchers.Default) {
                    val yStart = jobNumber * batchAmount
                    val yEnd = minOf(yStart + batchAmount, hInt)
                    for (y in yStart until yEnd) {
                        for (x in 0 until wInt) {
                            var zx = (x.toFloat() / wFloat) * zoomRangeX + (centerX - zoomRangeX / 2f)
                            var zy = (y.toFloat() / hFloat) * zoomRangeY + (centerY - zoomRangeY / 2f)
                            var iteration = 0
                            var color = Defaults.EscapeColor

                            while (iteration < iterationsUntilEscape) {
                                iteration++

                                val tempzx = zx
                                zx = zx * zx - zy * zy + constant.x
                                // Replace tempzx with zx for a scary face!
                                zy = 2 * tempzx * zy + constant.y

                                if (zx * zx + zy * zy > 4f) {
                                    break
                                }
                            }

                            if (iteration != iterationsUntilEscape) {
                                color = coloringMethod.toColor(zx, zy, iteration)
                            }

                            val index = (y * wInt + x) * 4
                            val argb = color.toArgb()

                            byteArray[index] = (argb).toByte()
                            byteArray[index + 1] = (argb shr 8).toByte()
                            byteArray[index + 2] = (argb shr 16).toByte()
                            byteArray[index + 3] = (argb shr 24).toByte()
                        }
                    }
                }
            }
        jobs.joinAll()
        return byteArray
    }
}

data class GenerationOptions(
    val iterationsUntilEscaped: Int = Defaults.IterationsUntilEscaped,
    val constant: Offset = Offset.Zero,
    val center: Offset = Offset.Zero,
    val escapeColor: Color = Defaults.EscapeColor,
    val zoomRange: Float = Defaults.Zoom,
    val coloringMethodIndex: Int
)