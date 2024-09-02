package com.emikhalets.superapp.core.common

/**
 * Конвертирование числового значения в строковой денежный формат
 */
fun Long?.convertMoney(): String {
    this ?: return ""
    val firstPart = if (this >= 100) this / 100 else 0
    val secondPart = this - firstPart * 100
    val secondPartString = if (secondPart < 10) "0$secondPart" else "$secondPart"
    return "$firstPart.$secondPartString"
}

/**
 * Конвертирование денежного значения в числовой формат
 */
fun String?.convertMoney(): Long {
    if (this.isNullOrBlank()) return 0
    val replaced = this.replace(",", "").replace(".", "")
    return replaced.toLongOrNull() ?: 0
}