package com.emikhalets.superapp.feature.salary.ui.chart

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.mvi.MviAction
import com.emikhalets.superapp.core.common.mvi.MviEffect
import com.emikhalets.superapp.core.common.mvi.MviState
import com.emikhalets.superapp.feature.salary.domain.SalaryModel

object ChartContract {

    @Immutable
    sealed class Action : MviAction {
        data class SaveSalary(val model: SalaryModel) : Action()
        data class DeleteSalary(val model: SalaryModel) : Action()
        data class SetEditSalary(val model: SalaryModel?) : Action()
    }

    @Immutable
    sealed class Effect : MviEffect {
        data class Error(val message: StringValue) : Effect()
    }

    @Immutable
    data class State(
        val salaryMap: Map<Long, Long> = emptyMap(),
        val salaryList: List<SalaryModel> = emptyList(),
        val editSalary: SalaryModel? = null,
    ) : MviState
}
