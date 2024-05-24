package com.emikhalets.superapp.feature.salary.edit_item

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.date.DateHelper
import com.emikhalets.superapp.core.common.mvi.MviAction
import com.emikhalets.superapp.core.common.mvi.MviEffect
import com.emikhalets.superapp.core.common.mvi.MviState
import com.emikhalets.superapp.domain.salary.model.SalaryModel
import com.emikhalets.superapp.domain.salary.model.SalaryType

object EditItemContract {

    @Immutable
    sealed class Action : MviAction {

        data object AddItem : Action()
        data class GetSalary(val id: Long) : Action()
        data class DeleteItem(val model: SalaryModel) : Action()
        data class SetValue(val value: String) : Action()
        data class SetDate(val date: Long?) : Action()
        data class SetType(val type: SalaryType) : Action()
        data class ShowDateDialog(val visible: Boolean) : Action()
    }

    @Immutable
    sealed class Effect : MviEffect

    @Immutable
    data class State(
        val itemId: Long = 0,
        val value: Long? = null,
        val date: Long = DateHelper.now,
        val type: SalaryType = SalaryType.SALARY,
        val showDateDialog: Boolean = false,
    ) : MviState
}
