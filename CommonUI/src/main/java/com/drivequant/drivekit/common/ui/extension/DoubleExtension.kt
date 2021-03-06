@file:JvmName("DKDoubleUtils")
package com.drivequant.drivekit.common.ui.extension

import java.text.DecimalFormat

fun Double.removeZeroDecimal(): String = DecimalFormat("0.#").format(this)

fun Double?.lessOrEqualsThan(other: Double): Boolean {
    return (this ?: return false) <= other
}

fun Double.convertKmsToMiles(): Double = this * 0.621371