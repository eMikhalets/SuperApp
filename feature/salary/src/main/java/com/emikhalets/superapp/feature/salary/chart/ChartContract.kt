package com.emikhalets.superapp.feature.salary.chart

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.mvi.MviAction
import com.emikhalets.superapp.core.common.mvi.MviEffect
import com.emikhalets.superapp.core.common.mvi.MviState
import com.emikhalets.superapp.domain.salary.SalaryModel
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer

object ChartContract {

    @Immutable
    sealed class Action : MviAction

    @Immutable
    sealed class Effect : MviEffect

    @Immutable
    data class State(
        val salaryList: List<SalaryModel> = emptyList(),
        val chartModelProducer: CartesianChartModelProducer = CartesianChartModelProducer.build(),
    ) : MviState
}
