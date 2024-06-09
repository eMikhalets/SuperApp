package com.emikhalets.superapp.feature.salary.edit_item

import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.date.DateHelper
import com.emikhalets.superapp.core.common.helper.MoneyHelper
import com.emikhalets.superapp.core.common.mvi.MviViewModel
import com.emikhalets.superapp.core.common.mvi.launch
import com.emikhalets.superapp.domain.salary.SalaryModel
import com.emikhalets.superapp.domain.salary.SalaryType
import com.emikhalets.superapp.domain.salary.use_case.DeleteSalaryUseCase
import com.emikhalets.superapp.domain.salary.use_case.GetSalaryUseCase
import com.emikhalets.superapp.domain.salary.use_case.InsertSalaryUseCase
import com.emikhalets.superapp.domain.salary.use_case.UpdateSalaryUseCase
import com.emikhalets.superapp.feature.salary.edit_item.EditItemContract.Action
import com.emikhalets.superapp.feature.salary.edit_item.EditItemContract.Effect
import com.emikhalets.superapp.feature.salary.edit_item.EditItemContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditItemViewModel @Inject constructor(
    private val getSalaryUseCase: GetSalaryUseCase,
    private val insertSalaryUseCase: InsertSalaryUseCase,
    private val updateSalaryUseCase: UpdateSalaryUseCase,
    private val deleteSalaryUseCase: DeleteSalaryUseCase,
) : MviViewModel<Action, Effect, State>() {

    override fun setInitialState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            Action.AddItem -> addSalary()
            is Action.GetSalary -> getSalary(action.id)
            is Action.DeleteItem -> deleteSalary(action.model)
            is Action.SetValue -> setSalaryValue(action.value)
            is Action.SetDate -> setSalaryDate(action.date)
            is Action.SetType -> setSalaryType(action.type)
        }
    }

    private fun setSalaryValue(value: String) {
        val convertedValue = MoneyHelper.convertMoney(value)
        setState { it.copy(value = convertedValue) }
    }

    private fun setSalaryDate(date: Long?) {
        val dateNotNull = date ?: DateHelper.now
        setState { it.copy(date = dateNotNull) }
    }

    private fun setSalaryType(type: SalaryType) {
        setState { it.copy(type = type) }
    }

    private fun addSalary() {
        val value = currentState.value ?: return
        val itemId = currentState.itemId
        val date = currentState.date
        val type = currentState.type
        val model = SalaryModel(itemId, value, date, type)
        dropSalaryDataInState()
        launch {
            if (itemId == 0L) {
                insertSalaryUseCase.invoke(model)
            } else {
                updateSalaryUseCase.invoke(model)
            }
        }
    }

    private fun getSalary(id: Long) {
        if (id == 0L) return
        launch {
            when (val result = getSalaryUseCase.invoke(id)) {
                is GetSalaryUseCase.Result.Failure -> setFailureState(result.message)
                is GetSalaryUseCase.Result.Success -> setSalaryItem(result.model)
            }
        }
    }

    private fun deleteSalary(model: SalaryModel) {
        launch {
            deleteSalaryUseCase.invoke(model)
        }
    }

    private fun setFailureState(message: StringValue) {
        // TODO show error dialog
    }

    private fun dropSalaryDataInState() {
        setState {
            it.copy(
                itemId = 0,
                value = null,
                date = DateHelper.now,
                type = SalaryType.SALARY,
            )
        }
    }

    private fun setSalaryItem(model: SalaryModel) {
        setState { it.copy(value = model.value, date = model.timestamp, type = model.type) }
    }
}
