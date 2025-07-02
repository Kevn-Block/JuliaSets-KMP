package io.sparkedember.juliaset.images

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap


fun ByteArray.encodeToImageBitmap(size: Size) =
    encodeToImageBitmap(size.width.toInt(), size.height.toInt(), this)

internal expect fun encodeToImageBitmap(width: Int, height: Int, bytes: ByteArray): ImageBitmap