package io.sparkedember.juliaset.images

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import java.nio.ByteBuffer

actual fun encodeToImageBitmap(
    width: Int,
    height: Int,
    bytes: ByteArray
): ImageBitmap =
    createBitmap(width, height).let {
        it.copyPixelsFromBuffer(ByteBuffer.wrap(bytes))
        it.asImageBitmap()
    }