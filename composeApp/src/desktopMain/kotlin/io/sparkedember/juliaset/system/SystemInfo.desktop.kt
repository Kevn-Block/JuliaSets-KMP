package io.sparkedember.juliaset.system

actual fun getAvailableProcessors(): Int =
    Runtime.getRuntime().availableProcessors()