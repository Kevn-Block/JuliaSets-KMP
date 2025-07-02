package io.sparkedember.juliaset.numbers

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(decimals: Int): Double {
    val multiplier = 10.0.pow(decimals)
    return (this * multiplier).roundToInt() / multiplier
}