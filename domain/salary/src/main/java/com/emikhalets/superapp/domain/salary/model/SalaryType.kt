package com.emikhalets.superapp.domain.salary.model

enum class SalaryType {
    SALARY,
    PREPAYMENT,
    OTHER;

    companion object {

        fun asStringList(): List<String> {
            return SalaryType.entries.map { it.toString() }
        }
    }
}

fun String.asSalaryType(): SalaryType {
    return SalaryType.valueOf(this)
}