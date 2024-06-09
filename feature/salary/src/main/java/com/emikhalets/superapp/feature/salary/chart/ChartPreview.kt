package com.emikhalets.superapp.feature.salary.chart

import com.emikhalets.superapp.core.common.date.DateHelper
import com.emikhalets.superapp.domain.salary.SalaryModel
import com.emikhalets.superapp.domain.salary.SalaryType

internal fun getChartSalaryList(): List<SalaryModel> {
    return listOf(
        SalaryModel(
            4000000,
            DateHelper.timestampOf(10, 3, 2023),
            SalaryType.SALARY
        ),
        SalaryModel(
            6000000,
            DateHelper.timestampOf(10, 4, 2023),
            SalaryType.SALARY
        ),
        SalaryModel(
            10000000,
            DateHelper.timestampOf(10, 6, 2023),
            SalaryType.SALARY
        ),
        SalaryModel(
            15000000,
            DateHelper.timestampOf(10, 7, 2023),
            SalaryType.SALARY
        ),
        SalaryModel(
            17600000,
            DateHelper.timestampOf(10, 9, 2023),
            SalaryType.SALARY
        ),
        SalaryModel(
            17600000,
            DateHelper.timestampOf(10, 10, 2023),
            SalaryType.SALARY
        ),
        SalaryModel(
            17600000,
            DateHelper.timestampOf(10, 11, 2023),
            SalaryType.SALARY
        ),
    )
}