package io.sparkedember.juliaset

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.sparkedember.juliaset.const.Defaults
import io.sparkedember.juliaset.const.DemoJuliaSets
import io.sparkedember.juliaset.geometry.GenerationOptions
import io.sparkedember.juliaset.geometry.JuliaSetGeneration
import io.sparkedember.juliaset.images.encodeToImageBitmap
import io.sparkedember.juliaset.numbers.roundTo
import io.sparkedember.juliaset.settings.SettingsConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit
import kotlin.time.measureTimedValue

class JuliaSetViewModel: ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private var animationJob: Job? = null

    fun load() {
        viewModelScope.launch(Dispatchers.Default) {
            _state
                .distinctUntilChanged { old, new ->
                    old.zoomRange == new.zoomRange &&
                            old.center == new.center &&
                            old.constant == new.constant &&
                            old.escapeColor == new.escapeColor &&
                            old.canvasSize == new.canvasSize &&
                            old.iterationsUntilEscaped == new.iterationsUntilEscaped &&
                            old.coloringMethodIndex == new.coloringMethodIndex
                }
                .collectLatest {
                    generateImage()
                }
        }
        runAnimationJob()
    }

    fun onCanvasSizeChanged(canvasSize: Size) {
        _state.update {
            it.copy(canvasSize = canvasSize)
        }
    }

    fun onResetToDefaults() {
        _state.update {
            State().copy(
                draftSettings = null,
                image = it.image,
                canvasSize = it.canvasSize
            )
        }
        viewModelScope.launch(Dispatchers.Default) {
            runAnimationJob()
            generateImage()
        }
    }

    fun onCenterChanged(center: Offset) {
        _state.update {
            it.copy(center = center)
        }
    }

    fun onZoomRangeChanged(zoomRange: Float) {
        _state.update {
            it.copy(zoomRange = zoomRange)
        }
    }

    fun onDraftSettingsChanged(draftSettings: SettingsConfig) {
        _state.update {
            it.copy(draftSettings = draftSettings)
        }
    }

    fun onApplySettings() {
        val draftSettings = _state.value.draftSettings
        if (draftSettings == null) {
            return
        }
        val selectedOffset = if (draftSettings.constantIndex == null || draftSettings.constantIndex == -1) {
            null
        } else {
            DemoJuliaSets[draftSettings.constantIndex]
        }
        _state.update {
            it.copy(
                draftSettings = null,
                animateConstantDelayMs = draftSettings.animateConstantMs,
                isAnimateConstantEnabled = draftSettings.isAnimateConstantEnabled,
                zoomRange = draftSettings.zoomRange,
                constant = selectedOffset?.offset ?: it.constant,
                iterationsUntilEscaped = draftSettings.iterationsUntilEscaped,
                coloringMethodIndex = draftSettings.coloringMethodIndex ?: it.coloringMethodIndex
            )
        }
        viewModelScope.launch {
            runAnimationJob()
            generateImage()
        }
    }

    private fun onConstantChanged(constant: Offset) {
        _state.update {
            it.copy(constant = constant)
        }
    }

    private fun runAnimationJob() {
        animationJob?.cancel()
        animationJob = viewModelScope.launch(Dispatchers.Default) {
            _state
                .distinctUntilChangedBy { it.isAnimateConstantEnabled }
                .distinctUntilChangedBy { it.animateConstantDelayMs }
                .collectLatest {
                    if (it.isAnimateConstantEnabled) {
                        animateConstant(it.constant, it.animateConstantDelayMs.toLong())
                    }
                }
        }
    }

    private suspend fun animateConstant(constant: Offset, delayMs: Long) {
        val startValue = -0.1
        val endValue = 0.1
        var currentConstant = constant
        val totalDuration = 2000L
        val numberOfSteps = (totalDuration / delayMs).toInt()
        val valuePerStep = ((endValue - startValue) / numberOfSteps).toFloat()

        while (true) {
            for (i in 0..numberOfSteps) {
                currentConstant = currentConstant.copy(x = currentConstant.x + valuePerStep)
                onConstantChanged(currentConstant)
                delay(delayMs)
            }
            delay(250)
            for (i in numberOfSteps downTo 0) {
                currentConstant = currentConstant.copy(x = currentConstant.x - valuePerStep)
                onConstantChanged(currentConstant)
                delay(delayMs)
            }
            delay(250)
        }
    }

    private suspend fun generateImage() {
        val timeTaken = measureTimedValue { generateAndSetBitmap() }
        val timeTakenInMs = timeTaken.duration.toDouble(DurationUnit.MILLISECONDS).roundTo(2)
        _state.update {
            it.copy(
                generationTimeMs = timeTakenInMs,
                image = timeTaken.value
            )
        }
    }

    private suspend fun generateAndSetBitmap(): ImageBitmap {
        val state = _state.value
        val rawByteArray = JuliaSetGeneration.generateByteArray(
            canvasSize = state.canvasSize,
            coroutineScope = viewModelScope,
            options = GenerationOptions(
                iterationsUntilEscaped = state.iterationsUntilEscaped,
                constant = state.constant,
                center = state.center,
                escapeColor = state.escapeColor,
                zoomRange = state.zoomRange,
                coloringMethodIndex = state.coloringMethodIndex
            )
        )
        return rawByteArray.encodeToImageBitmap(state.canvasSize)
    }

    data class State(
        val canvasSize: Size = Size.Unspecified,
        val constant: Offset = Defaults.JuliaSet.offset,
        val center: Offset = Offset.Zero,
        val image: ImageBitmap? = null,
        val iterationsUntilEscaped: Int = Defaults.IterationsUntilEscaped,
        val escapeColor: Color = Defaults.EscapeColor,
        val zoomRange: Float = Defaults.Zoom,
        val generationTimeMs: Double = 0.0,
        val isLoading: Boolean = false,
        val isAnimateConstantEnabled: Boolean = false,
        val animateConstantDelayMs: Int = Defaults.AnimationDelay,
        val lastTenRenderTimes: List<Double> = emptyList(),
        val coloringMethodIndex: Int = Defaults.ColoringMethodIndex,
        val draftSettings: SettingsConfig? = null
    ) {
        fun getConstantIndex(): Int =
            DemoJuliaSets.indexOfLast { it.offset == constant }
    }
}