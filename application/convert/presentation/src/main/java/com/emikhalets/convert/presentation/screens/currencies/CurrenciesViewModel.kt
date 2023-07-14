package com.emikhalets.convert.presentation.screens.currencies

import com.emikhalets.convert.domain.ConvertDataStore
import com.emikhalets.convert.domain.entity.CurrencyEntity
import com.emikhalets.convert.domain.usecase.AddCurrencyUseCase
import com.emikhalets.convert.domain.usecase.ConvertCurrencyUseCase
import com.emikhalets.convert.domain.usecase.DeleteCurrencyUseCase
import com.emikhalets.convert.domain.usecase.GetCurrenciesUseCase
import com.emikhalets.convert.domain.usecase.GetExchangesUseCase
import com.emikhalets.convert.presentation.screens.currencies.CurrenciesContract.Action
import com.emikhalets.convert.presentation.screens.currencies.CurrenciesContract.State
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getExchangesUseCase: GetExchangesUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val addCurrencyUseCase: AddCurrencyUseCase,
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val dataStore: ConvertDataStore,
) : BaseViewModel<Action, State>() {

    private var convertJob: Job? = null

    init {
        launchScope {
            dataStore.getCurrenciesDate { date ->
                logd(TAG, "Collecting date = $date")
                val startOfNextDay = date.localDate().atStartOfDay().plusDays(1).timestamp()
                setState { it.copy(date = date, isOldExchanges = startOfNextDay < Date().time) }
            }
        }
    }

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.DropError -> dropErrorState()
            Action.GetCurrencies -> getCurrencies()
            Action.GetExchanges -> getExchanges()
            is Action.AddCurrency -> addCurrency(action.code)
            is Action.DeleteCurrency -> deleteCurrency(action.code)
            is Action.Convert -> convert(action.value)
            is Action.NewCurrencyShow -> setNewCurrencyState(action.visible)
            is Action.NewCurrencyCode -> setState { it.copy(newCurrencyCode = action.code) }
            is Action.SetBaseCurrency -> setNewCurrencyState(action.code)
        }
    }

    private fun dropErrorState() {
        setState { it.copy(error = null) }
    }

    private fun setNewCurrencyState(visible: Boolean) {
        setState { it.copy(isNewCurrencyVisible = visible, newCurrencyCode = "") }
    }

    private fun setNewCurrencyState(code: String) {
        val newBase = if (code != currentState.baseCurrency) "" else currentState.baseValue
        setState { it.copy(baseCurrency = code, baseValue = newBase) }
    }

    private fun getCurrencies() {
        logd(TAG, "Get currencies")
        launchScope {
            getCurrenciesUseCase()
                .onSuccess { flow -> setCurrenciesFlow(flow) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun getExchanges() {
        logd(TAG, "Get exchanges")
        launchScope {
            setState { it.copy(isLoading = true) }
            getExchangesUseCase()
                .onSuccess { list -> setState { it.copy(exchanges = list, isLoading = false) } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun addCurrency(code: String) {
        logd(TAG, "Add currency: code = $code")
        if (code.isBlank()) return
        launchScope {
            setNewCurrencyState(false)
            addCurrencyUseCase(code.uppercase())
                .onSuccess { getExchanges() }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun deleteCurrency(code: String) {
        logd(TAG, "Delete currency: code = $code")
        if (code.isBlank()) return
        launchScope {
            deleteCurrencyUseCase(code)
                .onSuccess {}
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun convert(value: String) {
        setState { it.copy(baseValue = value) }
        if (value.isBlank()) return
        if (convertJob?.isActive == true) convertJob?.cancel()
        convertJob = launchScope {
            delay(200)
            logd(TAG, "Convert value: base = ${currentState.baseCurrency}, value = $value")
            value.toDoubleOrNull()?.let { convertedValue ->
                val currencies = currentState.currencies
                val exchanges = currentState.exchanges
                convertCurrencyUseCase
                    .invoke(currencies, exchanges, currentState.baseCurrency, convertedValue)
                    .onSuccess { result -> setState { it.copy(currencies = result) } }
                    .onFailure { code, message -> handleFailure(code, message) }
            }
        }
    }

    private suspend fun setCurrenciesFlow(flow: Flow<List<CurrencyEntity>>) {
        flow.collectLatest { list ->
            logd(TAG, "Collecting currencies: $list")
            val currencies = list.map { Pair(it.code, 0.0) }
            setState { it.copy(currencies = currencies) }
        }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        logd(TAG, "Handle error: code = $code")
        setState { it.copy(isLoading = false, error = message) }
    }

    companion object {

        private const val TAG = "CurrenciesVM"
    }
}
