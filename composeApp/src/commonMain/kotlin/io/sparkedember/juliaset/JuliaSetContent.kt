package io.sparkedember.juliaset

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import io.sparkedember.juliaset.geometry.modifiers.rectZoomModifier

@Composable
fun JuliaSetContent(
    modifier: Modifier,
    image: ImageBitmap?,
    canvasSize: Size,
    zoomRange: Float,
    center: Offset,
    rectStrokeColor: Color,
    onCanvasSizeChanged: (Size) -> Unit,
    onCenterChanged: (Offset) -> Unit,
    onZoomRangeChanged: (Float) -> Unit,
) {
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
        image?.let {
            drawImage(it)
        }
    }
}