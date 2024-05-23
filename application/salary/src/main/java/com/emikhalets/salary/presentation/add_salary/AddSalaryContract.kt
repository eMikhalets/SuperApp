package com.emikhalets.salary.presentation.add_salary

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.helper.DateHelper
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.salary.domain.model.SalaryModel
import com.emikhalets.salary.domain.model.SalaryType

object AddSalaryContract {

    @Immutable
    sealed class Action : UiAction {

        data object AddSalary : Action()
        data class DeleteSalary(val model: SalaryModel) : Action()
        data class SetCurrentSalaryValue(val value: String) : Action()
        data class SetCurrentSalaryDate(val date: Long?) : Action()
        data class SetCurrentSalaryType(val type: SalaryType) : Action()
        data class SetDateDialogVisible(val visible: Boolean) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect

    @Immutable
    data class State(
        val currentSalaryValue: Long? = null,
        val currentSalaryDate: Long = DateHelper.nowTimestamp,
        val currentSalaryType: SalaryType = SalaryType.SALARY,
        val showDateDialog: Boolean = false,
    ) : UiState
}