package io.sparkedember.juliaset.images

import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorInfo
import org.jetbrains.skia.ColorSpace
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo

actual fun encodeToImageBitmap(
    width: Int,
    height: Int,
    bytes: ByteArray
): androidx.compose.ui.graphics.ImageBitmap {
    val skiaImage = Image.makeRaster(
        imageInfo = ImageInfo(
            colorInfo = ColorInfo(
                colorType = ColorType.RGBA_8888,
                alphaType = ColorAlphaType.PREMUL,
                colorSpace = ColorSpace.sRGB
            ),
            width = width,
            height = height
        ),
        bytes = bytes,
        rowBytes = width * 4
    )
    return skiaImage.toComposeImageBitmap()
}