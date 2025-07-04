package io.sparkedember.juliaset.shaders

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
actual fun juliaSetShader(
    script: String,
    params: JuliaSetShaderParams
): Shader {
    val shader = RuntimeShader(script).apply {
        val size = params.canvasSize
        val center = params.center
        val constant = params.constant
        val maxIterations = params.maxIterations
        setFloatUniform("iResolution",size.width, size.height)
        setFloatUniform("center", center.x, center.y)
        setFloatUniform("zoomRange", params.zoomRange)
        setFloatUniform("constant", constant.x, constant.y)
        setIntUniform("maxIterations", maxIterations)
        setIntUniform("coloringMethod", params.coloringMethod)
    }

    val brush = ShaderBrush(shader)
    return brush.createShader(params.canvasSize)
}