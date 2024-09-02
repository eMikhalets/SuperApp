package com.emikhalets.superapp.feature.salary.ui.chart

import androidx.lifecycle.viewModelScope
import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.common.mvi.MviViewModel
import com.emikhalets.superapp.core.common.mvi.launch
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.emikhalets.superapp.feature.salary.domain.use_case.DeleteSalaryUseCase
import com.emikhalets.superapp.feature.salary.domain.use_case.GetSalariesUseCase
import com.emikhalets.superapp.feature.salary.domain.use_case.InsertSalaryUseCase
import com.emikhalets.superapp.feature.salary.domain.use_case.UpdateSalaryUseCase
import com.emikhalets.superapp.feature.salary.ui.chart.ChartContract.Action
import com.emikhalets.superapp.feature.salary.ui.chart.ChartContract.Effect
import com.emikhalets.superapp.feature.salary.ui.chart.ChartContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getSalariesUseCase: GetSalariesUseCase,
    private val insertSalaryUseCase: InsertSalaryUseCase,
    private val updateSalaryUseCase: UpdateSalaryUseCase,
    private val deleteSalaryUseCase: DeleteSalaryUseCase,
) : MviViewModel<Action, Effect, State>() {

    private val salariesFlow: Flow<List<SalaryModel>> =
        flow { emitAll(getSalariesUseCase.invoke()) }
            .catch { setFailureState(it) }
            .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    init {
        launch {
            salariesFlow.collect { setSalariesState(it) }
        }
    }

    override fun setInitialState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            is Action.DeleteSalary -> deleteSalary(action.model)
            is Action.SaveSalary -> saveSalary(action.model)
            is Action.SetEditSalary -> setEditSalary(action.model)
        }
    }

    private fun setEditSalary(model: SalaryModel?) {
        setState { it.copy(editSalary = model) }
    }

    private fun saveSalary(model: SalaryModel) {
        if (model.id == Const.IdNew) {
            insertSalary(model)
        } else {
            updateSalary(model)
        }
    }

    private fun insertSalary(model: SalaryModel) {
        launch {
            when (val result = insertSalaryUseCase.invoke(model)) {
                is AppResult.Failure -> setFailureState(result.exception)
                is AppResult.Success -> setState { it.copy(editSalary = null) }
            }
        }
    }

    private fun updateSalary(model: SalaryModel) {
        launch {
            when (val result = updateSalaryUseCase.invoke(model)) {
                is AppResult.Failure -> setFailureState(result.exception)
                is AppResult.Success -> setState { it.copy(editSalary = null) }
            }
        }
    }

    private fun deleteSalary(model: SalaryModel) {
        launch {
            when (val result = deleteSalaryUseCase.invoke(model)) {
                is AppResult.Failure -> setFailureState(result.exception)
                is AppResult.Success -> setState { it.copy(editSalary = null) }
            }
        }
    }

    private fun setSalariesState(data: List<SalaryModel>) {
        Timber.d("Salaries list size = ${data.size}")
        val list = data.sortedByDescending { it.timestamp }
        val map = data.associate { it.timestamp to it.value / 100 }
        setState { it.copy(salaryList = list, salaryMap = map) }
    }

    private fun setFailureState(throwable: Throwable?) {
        setEffect { Effect.Error(StringValue.exception(throwable)) }
    }
}
