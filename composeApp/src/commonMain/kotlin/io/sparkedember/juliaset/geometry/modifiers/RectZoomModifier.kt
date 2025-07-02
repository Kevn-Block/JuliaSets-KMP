package io.sparkedember.juliaset.geometry.modifiers

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import io.sparkedember.juliaset.geometry.aspectRatio
import io.sparkedember.juliaset.geometry.fromOffsets

fun Modifier.rectZoomModifier(
    canvasSize: Size,
    currentCenter: Offset,
    currentZoomRange: Float,
    strokeColor: Color,
    onDragEnd: (center: Offset, zoomRange: Float) -> Unit,
): Modifier =
    composed {
        var initialPoint by remember { mutableStateOf(Offset.Unspecified) }
        var drawnRect by remember { mutableStateOf(Rect.Zero) }
        pointerInput(canvasSize, currentCenter) {
            detectDragGestures(
                onDragStart = { clickPoint ->
                    initialPoint = clickPoint
                },
                onDragEnd = {
                    val w = canvasSize.width
                    val h = canvasSize.height
                    val aspectRatio = canvasSize.aspectRatio
                    val oldZoomRangeY = currentZoomRange
                    val oldZoomRangeX = oldZoomRangeY * aspectRatio

                    fun getNewCenter(pixel: Offset): Offset {
                        val real = (pixel.x / w) * oldZoomRangeX + (currentCenter.x - oldZoomRangeX / 2f)
                        val imag = (pixel.y / h) * oldZoomRangeY + (currentCenter.y - oldZoomRangeY / 2f)
                        return Offset(real, imag)
                    }

                    val newCenter = getNewCenter(drawnRect.center)
                    val newRange = oldZoomRangeY * (drawnRect.height / h)

                    drawnRect = Rect.Zero
                    onDragEnd(
                        newCenter,
                        newRange
                    )
                },
                onDragCancel = {
                    drawnRect = Rect.Zero
                },
                onDrag = { change, dragAmount ->
                    change.consume()
                    drawnRect = Rect.Companion.fromOffsets(initialPoint, change.position)
                }
            )
        }
        .drawWithContent {
            drawContent()
            if (drawnRect != Rect.Zero) {
                drawRect(
                    color = strokeColor,
                    topLeft = drawnRect.topLeft,
                    size = drawnRect.size,
                    style = Stroke(width = 3f)
                )
            }
        }
    }