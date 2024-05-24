package com.emikhalets.superapp.feature.convert.currencies

import androidx.lifecycle.viewModelScope
import com.emikhalets.superapp.core.common.mvi.MviViewModel
import com.emikhalets.superapp.core.common.mvi.launch
import com.emikhalets.superapp.domain.convert.model.CurrencyModel
import com.emikhalets.superapp.domain.convert.model.CurrencyPairModel
import com.emikhalets.superapp.domain.convert.use_case.ConvertCurrencyUseCase
import com.emikhalets.superapp.domain.convert.use_case.DeleteCurrencyUseCase
import com.emikhalets.superapp.domain.convert.use_case.GetCurrenciesUseCase
import com.emikhalets.superapp.domain.convert.use_case.GetExchangesUseCase
import com.emikhalets.superapp.domain.convert.use_case.InsertCurrencyUseCase
import com.emikhalets.superapp.domain.convert.use_case.UpdateExchangesUseCase
import com.emikhalets.superapp.feature.convert.currencies.CurrenciesContract.Action
import com.emikhalets.superapp.feature.convert.currencies.CurrenciesContract.Effect
import com.emikhalets.superapp.feature.convert.currencies.CurrenciesContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getExchangesUseCase: GetExchangesUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val insertCurrencyUseCase: InsertCurrencyUseCase,
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val updateExchangesUseCase: UpdateExchangesUseCase,
) : MviViewModel<Action, Effect, State>() {

    private var updatingJob: Job? = null

    private val currenciesFlow: Flow<List<CurrencyModel>> =
        flow { emitAll(getCurrenciesUseCase()) }
            .catch { setFailureState(it) }
            .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    private val exchangesFlow: Flow<List<CurrencyPairModel>> =
        flow { emitAll(getExchangesUseCase()) }
            .catch { setFailureState(it) }
            .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    init {
        launch {
            exchangesFlow.collect { setExchangesState(it) }
        }
        launch {
            currenciesFlow.collect { setCurrenciesState(it) }
        }
    }

    override fun setInitialState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            Action.AddCurrency -> addCurrency()
            Action.UpdateExchanges -> updateExchanges()
            is Action.DeleteCurrency -> deleteCurrency(action.code)
            is Action.SetBaseCode -> setBaseCode(action.code)
            is Action.SetBaseValue -> setBaseValue(action.value)
            is Action.SetNewCurrencyCode -> setNewCurrencyCode(action.code)
            is Action.SetNewCurrencyVisible -> setNewCurrencyVisible(action.visible)
        }
    }

    private fun setNewCurrencyCode(code: String) {
        if (currentState.newCurrencyCode != code) {
            setState { it.copy(newCurrencyCode = code) }
        }
    }

    private fun setNewCurrencyVisible(visible: Boolean) {
        if (currentState.newCurrencyVisible != visible) {
            setState { it.copy(newCurrencyVisible = visible, newCurrencyCode = "") }
        }
    }

    private fun setBaseCode(code: String) {
        if (currentState.baseCode != code) {
            setState { it.copy(baseCode = code, baseValue = "") }
        }
    }

    private fun setBaseValue(value: String) {
        Timber.d("set base value: $value")
        if (currentState.baseValue != value) {
            setState { it.copy(baseValue = value) }
//            convert(value)
        }
    }

    private fun updateExchanges() {
        if (updatingJob?.isActive == true) updatingJob?.cancel()
        updatingJob = launch {
            setState { it.copy(loading = true) }
            updateExchangesUseCase.invoke(currentState.exchanges)
            setState { it.copy(loading = false) }
        }
    }

    private fun addCurrency() {
        launch {
            val code = currentState.newCurrencyCode
            // TODO add new code validation
            if (code.isNotBlank()) {
                insertCurrencyUseCase.invoke(code.take(3).uppercase())
            }
            setState { it.copy(newCurrencyCode = "", newCurrencyVisible = false) }
        }
    }

    private fun deleteCurrency(code: String) {
        launch {
            if (code.isNotBlank()) {
                if (currentState.baseCode == code) {
                    setState { it.copy(baseCode = "") }
                }
                deleteCurrencyUseCase(code)
            }
        }
    }

    private fun convert(baseValue: String) {
        launch {
            val newList = convertCurrencyUseCase.invoke(
                pairs = currentState.pairList,
                exchanges = currentState.exchanges,
                baseCode = currentState.baseCode,
                baseValue = baseValue
            )
            setState { it.copy(pairList = newList) }
        }
    }

    private fun setCurrenciesState(list: List<CurrencyModel>) {
        val currencies = list.map { it.code }
        val pairs = list.map { Pair(it.code, "") }
        setState { it.copy(pairList = pairs, currencies = currencies) }
    }

    private fun setExchangesState(list: List<CurrencyPairModel>) {
        val needUpdate = list.any { it.isNeedUpdate() }
        val updatedDate = list.minByOrNull { it.date }?.date ?: 0
        setState { it.copy(exchanges = list, date = updatedDate, isOldExchanges = needUpdate) }
    }

    private fun setFailureState(throwable: Throwable) {
        // TODO show error dialog
    }
}
