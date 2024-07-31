package com.emikhalets.superapp.feature.salary.ui.chart

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.mvi.MviAction
import com.emikhalets.superapp.core.common.mvi.MviEffect
import com.emikhalets.superapp.core.common.mvi.MviState
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer

object ChartContract {

    @Immutable
    sealed class Action : MviAction {
        data class SaveSalary(val model: SalaryModel) : Action()
        data class DeleteSalary(val model: SalaryModel) : Action()
        data class SetEditSalary(val model: SalaryModel?) : Action()
    }

    @Immutable
    sealed class Effect : MviEffect

    @Immutable
    data class State(
        val salaryList: List<SalaryModel> = emptyList(),
        val chartModelProducer: CartesianChartModelProducer = CartesianChartModelProducer.build(),
        val editSalary: SalaryModel? = null,
    ) : MviState
}
