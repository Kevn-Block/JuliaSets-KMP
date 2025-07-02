package io.sparkedember.juliaset.system

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual fun getAvailableProcessors(): Int =
    Platform.getAvailableProcessors()