package com.emikhalets.salary.presentation.chart

import androidx.lifecycle.viewModelScope
import com.emikhalets.core.common.helper.DateHelper
import com.emikhalets.core.common.helper.MoneyHelper
import com.emikhalets.core.common.mvi.MviViewModel
import com.emikhalets.core.common.mvi.launch
import com.emikhalets.salary.domain.model.SalaryModel
import com.emikhalets.salary.domain.model.SalaryType
import com.emikhalets.salary.domain.use_case.DeleteSalaryUseCase
import com.emikhalets.salary.domain.use_case.GetSalariesUseCase
import com.emikhalets.salary.domain.use_case.InsertSalaryUseCase
import com.emikhalets.salary.domain.use_case.UpdateSalaryUseCase
import com.emikhalets.salary.presentation.chart.ChartContract.Action
import com.emikhalets.salary.presentation.chart.ChartContract.Effect
import com.emikhalets.salary.presentation.chart.ChartContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getSalariesUseCase: GetSalariesUseCase,
    private val insertSalaryUseCase: InsertSalaryUseCase,
    private val updateSalaryUseCase: UpdateSalaryUseCase,
    private val deleteSalaryUseCase: DeleteSalaryUseCase,
) : MviViewModel<Action, Effect, State>() {

    private val salariesFlow: Flow<List<SalaryModel>> =
        flow { emitAll(getSalariesUseCase.invoke()) }.catch { setFailureState(it) }
            .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    init {
        launch {
            salariesFlow.collect { setSalariesState(it) }
        }
    }

    override fun setInitialState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            Action.AddSalary -> addSalary()
            is Action.DeleteSalary -> deleteSalary(action.model)
            is Action.SetCurrentSalaryValue -> setSalaryValue(action.value)
            is Action.SetCurrentSalaryDate -> setSalaryDate(action.date)
            is Action.SetCurrentSalaryType -> setSalaryType(action.type)
            is Action.SetDateDialogVisible -> setDateDialogVisible(action.visible)
        }
    }

    private fun setSalaryValue(value: String) {
        val convertedValue = MoneyHelper.convertMoney(value)
        setState { it.copy(currentSalaryValue = convertedValue) }
    }

    private fun setSalaryDate(date: Long?) {
        val dateNotNull = date ?: DateHelper.nowTimestamp
        setState { it.copy(currentSalaryDate = dateNotNull, showDateDialog = false) }
    }

    private fun setSalaryType(type: SalaryType) {
        setState { it.copy(currentSalaryType = type) }
    }

    private fun setDateDialogVisible(visible: Boolean) {
        setState { it.copy(showDateDialog = visible) }
    }

    private fun addSalary() {
        launch {
            val value = currentState.currentSalaryValue ?: return@launch
            val date = currentState.currentSalaryDate
            val type = currentState.currentSalaryType
            val model = SalaryModel(value, date, type)
            dropSalaryDataInState()
            insertSalaryUseCase.invoke(model)
        }
    }

    private fun deleteSalary(model: SalaryModel) {
        launch {
            deleteSalaryUseCase.invoke(model)
        }
    }

    private fun setSalariesState(list: List<SalaryModel>) {
        setState { it.copy(salaryList = list) }
    }

    private fun setFailureState(throwable: Throwable) {
        // TODO show error dialog
    }

    private fun dropSalaryDataInState() {
        setState {
            it.copy(
                currentSalaryValue = null,
                currentSalaryDate = DateHelper.nowTimestamp,
                currentSalaryType = SalaryType.SALARY,
            )
        }
    }
}
