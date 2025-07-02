package io.sparkedember.juliaset.system

import kotlinx.browser.window

actual fun getAvailableProcessors(): Int =
    window.navigator.hardwareConcurrency.toInt()