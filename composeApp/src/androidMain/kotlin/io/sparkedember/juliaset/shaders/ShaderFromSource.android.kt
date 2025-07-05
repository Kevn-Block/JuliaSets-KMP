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
        setFloatUniform("fCenter", center.x, center.y)
        setFloatUniform("fZoom", params.zoomRange)
        setFloatUniform("fConstant", constant.x, constant.y)
        setIntUniform("iMaxIterations", maxIterations)
        setIntUniform("iColorMethod", params.coloringMethod)
    }

    val brush = ShaderBrush(shader)
    return brush.createShader(params.canvasSize)
}