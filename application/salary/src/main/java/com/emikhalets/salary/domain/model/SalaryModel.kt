package com.emikhalets.salary.domain.model

data class SalaryModel(
    val id: Long,
    val value: Long,
    val timestamp: Long,
    val type: SalaryType,
)
