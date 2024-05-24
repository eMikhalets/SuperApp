package com.emikhalets.superapp.core.common.helper

object MoneyHelper {

    fun convertMoney(value: Long?): String {
        if (value == null) return ""
        val firstPart = if (value >= 100) value / 100 else 0
        val secondPart = value - firstPart * 100
        val secondPartString = if (secondPart < 10) "0$secondPart" else "$secondPart"
        return "$firstPart.$secondPartString"
    }

    fun convertMoney(value: String): Long? {
        if (value.isBlank()) return null
        val replaced = value.replace(",", "").replace(".", "")
        return replaced.toLong()
    }
}
