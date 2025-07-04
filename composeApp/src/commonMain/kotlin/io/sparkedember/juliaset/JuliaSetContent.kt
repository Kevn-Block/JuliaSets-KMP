package io.sparkedember.juliaset

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import io.sparkedember.juliaset.geometry.modifiers.rectZoomModifier
import io.sparkedember.juliaset.shaders.JuliaSetShader
import io.sparkedember.juliaset.shaders.JuliaSetShaderParams
import io.sparkedember.juliaset.shaders.juliaSetShader

@Composable
fun JuliaSetContent(
    modifier: Modifier,
    image: ImageBitmap?,
    canvasSize: Size,
    zoomRange: Float,
    center: Offset,
    constant: Offset,
    maxIterations: Int,
    coloringMethod: Int,
    rectStrokeColor: Color,
    isGPURenderingOn: Boolean,
    isAnimateConstantEnabled: Boolean,
    onCanvasSizeChanged: (Size) -> Unit,
    onCenterChanged: (Offset) -> Unit,
    onZoomRangeChanged: (Float) -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val animatedOffset by infiniteTransition.animateFloat(
        0f,
        0.05f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                0f at 500
                0.5f at 1250
                1f at 2000
            },
            repeatMode = RepeatMode.Reverse,

        ),
    )

    Canvas(
        modifier = modifier
            .rectZoomModifier(
                canvasSize = canvasSize,
                currentZoomRange = zoomRange,
                currentCenter = center,
                strokeColor = rectStrokeColor,
                onDragEnd = { newCenter, newZoomRange ->
                    onCenterChanged(newCenter)
                    onZoomRangeChanged(newZoomRange)
                }
            )
            .onSizeChanged {
                onCanvasSizeChanged(it.toSize())
            }
    ) {

        if (isGPURenderingOn) {
            val shader = juliaSetShader(
                script = JuliaSetShader,
                params = JuliaSetShaderParams(
                    canvasSize = size,
                    center = center,
                    constant = if (isAnimateConstantEnabled) constant.copy(constant.x + animatedOffset, constant.y) else constant,
                    zoomRange = zoomRange,
                    maxIterations = maxIterations,
                    coloringMethod = coloringMethod
                ),
            )
            drawRect(ShaderBrush(shader))
        } else {
            image?.let {
                drawImage(it)
            }
        }
    }
}