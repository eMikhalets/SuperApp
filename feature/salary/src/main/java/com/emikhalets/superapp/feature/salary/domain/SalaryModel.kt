package com.emikhalets.superapp.feature.salary.domain

import java.util.Date

data class SalaryModel(
    val id: Long,
    val value: Long,
    val timestamp: Long,
    val type: SalaryType,
) {

    constructor() : this(0, Date().time, SalaryType.SALARY)

    constructor(value: Long, timestamp: Long, type: SalaryType) : this(0, value, timestamp, type)
}
