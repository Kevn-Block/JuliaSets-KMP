package io.sparkedember.juliaset.geometry

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

fun Rect.Companion.fromOffsets(firstOffset: Offset, secondOffset: Offset): Rect {
    val left = minOf(firstOffset.x, secondOffset.x)
    val top = minOf(firstOffset.y, secondOffset.y)
    val right = maxOf(firstOffset.x, secondOffset.x)
    val bottom = maxOf(firstOffset.y, secondOffset.y)

    return Rect(
        topLeft = Offset(left, top),
        bottomRight = Offset(right, bottom)
    )
}

val Size.aspectRatio get() =
    width / height