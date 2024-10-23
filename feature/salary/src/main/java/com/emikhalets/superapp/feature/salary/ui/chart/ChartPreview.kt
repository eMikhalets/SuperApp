package com.emikhalets.superapp.feature.salary.ui.chart

import com.emikhalets.superapp.core.common.timestampOf
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.emikhalets.superapp.feature.salary.domain.SalaryType

internal fun getChartSalaryList(): List<SalaryModel> {
    return listOf(
        SalaryModel(4000000, timestampOf(10, 3, 2023), SalaryType.SALARY),
        SalaryModel(6000000, timestampOf(10, 4, 2023), SalaryType.PREPAYMENT),
        SalaryModel(10000000, timestampOf(10, 6, 2023), SalaryType.OTHER),
        SalaryModel(15000000, timestampOf(10, 7, 2023), SalaryType.SALARY),
        SalaryModel(17600000, timestampOf(10, 9, 2023), SalaryType.SALARY),
        SalaryModel(17600000, timestampOf(10, 10, 2023), SalaryType.SALARY),
        SalaryModel(17600000, timestampOf(10, 11, 2023), SalaryType.SALARY),
    )
}