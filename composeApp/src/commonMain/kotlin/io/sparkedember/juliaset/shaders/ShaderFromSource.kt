package io.sparkedember.juliaset.shaders

import androidx.compose.ui.graphics.Shader

expect fun juliaSetShader(script: String, params: JuliaSetShaderParams): Shader

