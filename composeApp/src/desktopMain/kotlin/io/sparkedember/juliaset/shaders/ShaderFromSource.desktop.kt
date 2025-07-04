package io.sparkedember.juliaset.shaders

import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import org.jetbrains.skia.RuntimeEffect
import org.jetbrains.skia.RuntimeShaderBuilder

actual fun juliaSetShader(
    script: String,
    params: JuliaSetShaderParams
): Shader {
    val shader = RuntimeEffect.makeForShader(script)
    val builder = RuntimeShaderBuilder(shader).apply {
        val size = params.canvasSize
        val center = params.center
        val constant = params.constant
        val maxIterations = params.maxIterations
        uniform("iResolution",size.width, size.height)
        uniform("center", center.x, center.y)
        uniform("zoomRange", params.zoomRange)
        uniform("constant", constant.x, constant.y)
        uniform("maxIterations", maxIterations)
    }

    val brush = ShaderBrush(builder.makeShader())
    return brush.createShader(params.canvasSize)
}