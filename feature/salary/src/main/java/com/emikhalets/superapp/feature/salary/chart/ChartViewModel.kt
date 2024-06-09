package com.emikhalets.superapp.feature.salary.chart

import androidx.lifecycle.viewModelScope
import com.emikhalets.superapp.core.common.mvi.MviViewModel
import com.emikhalets.superapp.core.common.mvi.launch
import com.emikhalets.superapp.domain.salary.SalaryModel
import com.emikhalets.superapp.domain.salary.use_case.GetSalariesUseCase
import com.emikhalets.superapp.feature.salary.chart.ChartContract.Action
import com.emikhalets.superapp.feature.salary.chart.ChartContract.Effect
import com.emikhalets.superapp.feature.salary.chart.ChartContract.State
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
        // Nothing
    }

    private fun setSalariesState(list: List<SalaryModel>) {
        setState { it.copy(salaryList = list) }
    }

    private fun setFailureState(throwable: Throwable) {
        // TODO show error dialog
    }
}
