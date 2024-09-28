package com.emikhalets.superapp.feature.convert.domain

import com.emikhalets.superapp.core.common.localDate
import com.emikhalets.superapp.core.common.timestamp

data class ExchangeModel(
    val id: Long,
    val mainCode: String,
    val subCode: String,
    val value: Double,
    val updateDate: Long,
) {

    constructor(mainCode: String) : this(0, mainCode, "", 0.0, 0)

    constructor(mainCode: String, subCode: String) : this(0, mainCode, subCode, 0.0, 0)

    val fullCode: String
        get() = "$mainCode$subCode"

    fun isNeedUpdate(): Boolean {
        if (subCode.isEmpty()) return false
        if (updateDate == 0L) return true
        val startOfNextDay = updateDate
            .localDate()
            .atStartOfDay()
            .plusDays(1)
            .timestamp()
        return startOfNextDay < timestamp()
    }
}

fun List<ExchangeModel>.buildCodesList(): List<String> {
    val codes = mutableSetOf<String>()
    this.forEach {
        codes.add(it.mainCode)
        codes.add(it.subCode)
    }
    return codes.filter { it.isNotBlank() }.toList()
}
