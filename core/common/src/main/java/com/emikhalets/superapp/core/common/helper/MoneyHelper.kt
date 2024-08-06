package com.emikhalets.superapp.core.common.helper

object MoneyHelper {

    /**
     * Конвертирование числового значения в строковой денежный формат
     */
    fun convertMoney(value: Long): String {
        val firstPart = if (value >= 100) value / 100 else 0
        val secondPart = value - firstPart * 100
        val secondPartString = if (secondPart < 10) "0$secondPart" else "$secondPart"
        return "$firstPart.$secondPartString"
    }

    /**
     * Конвертирование денежного значения в числовой формат
     */
    fun convertMoney(value: String): Long {
        if (value.isBlank()) return 0
        val replaced = value.replace(",", "").replace(".", "")
        return replaced.toLongOrNull() ?: 0
    }
}
