package com.emikhalets.salary.presentation.add_salary

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.helper.DateHelper
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.superapp.domain.salary.model.SalaryModel
import com.emikhalets.superapp.domain.salary.model.SalaryType

object AddSalaryContract {

    @Immutable
    sealed class Action : UiAction {

        data object AddSalary : Action()
        data class DeleteSalary(val model: com.emikhalets.superapp.domain.salary.model.SalaryModel) : Action()
        data class SetCurrentSalaryValue(val value: String) : Action()
        data class SetCurrentSalaryDate(val date: Long?) : Action()
        data class SetCurrentSalaryType(val type: com.emikhalets.superapp.domain.salary.model.SalaryType) : Action()
        data class SetDateDialogVisible(val visible: Boolean) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect

    @Immutable
    data class State(
        val currentSalaryValue: Long? = null,
        val currentSalaryDate: Long = DateHelper.nowTimestamp,
        val currentSalaryType: com.emikhalets.superapp.domain.salary.model.SalaryType = com.emikhalets.superapp.domain.salary.model.SalaryType.SALARY,
        val showDateDialog: Boolean = false,
    ) : UiState
}
