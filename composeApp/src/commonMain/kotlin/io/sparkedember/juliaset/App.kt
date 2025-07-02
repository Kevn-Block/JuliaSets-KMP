package io.sparkedember.juliaset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.sparkedember.juliaset.components.AppHeader
import io.sparkedember.juliaset.settings.SettingsConfig
import io.sparkedember.juliaset.settings.SettingsSheet
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    viewModel: JuliaSetViewModel = viewModel { JuliaSetViewModel() }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    ModalNavigationDrawer(
        modifier = Modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                SettingsSheet(
                    activeSettings = SettingsConfig(
                        isAnimateConstantEnabled = state.draftSettings?.isAnimateConstantEnabled ?: state.isAnimateConstantEnabled,
                        animateConstantMs = state.animateConstantDelayMs,
                        zoomRange = state.zoomRange,
                        constant = state.constant,
                        iterationsUntilEscaped = state.iterationsUntilEscaped,
                        coloringMethodIndex = state.coloringMethodIndex
                    ),
                    onDraftSettingsChanged = {
                        viewModel.onDraftSettingsChanged(it)
                    },
                    onApply = {
                        viewModel.onApplySettings()
                    },
                    onResetToDefaults = {
                        viewModel.onResetToDefaults()
                    }
                )
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            ) {
                JuliaSetContent(
                    modifier = Modifier
                        .fillMaxSize(),
                    image = state.image,
                    canvasSize = state.canvasSize,
                    zoomRange = state.zoomRange,
                    center = state.center,
                    rectStrokeColor = Color.Red,
                    onCenterChanged = viewModel::onCenterChanged,
                    onZoomRangeChanged = viewModel::onZoomRangeChanged,
                    onCanvasSizeChanged = viewModel::onCanvasSizeChanged,
                )
                AppHeader(
                    generationTimeInMs = state.generationTimeMs,
                    onSettingsClicked = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
        }
    )
}