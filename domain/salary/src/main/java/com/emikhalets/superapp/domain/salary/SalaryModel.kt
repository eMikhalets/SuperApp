package com.emikhalets.superapp.domain.salary

data class SalaryModel(
    val id: Long,
    val value: Long,
    val timestamp: Long,
    val type: SalaryType,
) {

    constructor(value: Long, timestamp: Long, type: SalaryType) : this(0, value, timestamp, type)
}
