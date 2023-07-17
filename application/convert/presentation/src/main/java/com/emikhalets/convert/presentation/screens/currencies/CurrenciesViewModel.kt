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
import com.emikhalets.core.common.toIntOrNull
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
            is Action.NewCurrencyEvent -> setNewCurrencyState(action.code, action.visible)
            is Action.BaseCurrencyEvent -> setBaseCurrencyState(action.code, action.value)
        }
    }

    private fun dropErrorState() {
        setState { it.copy(error = null) }
    }

    private fun setNewCurrencyState(code: String, visible: Boolean) {
        if (currentState.newCurrencyCode != code) {
            setState { it.copy(newCurrencyCode = code) }
        }
        if (currentState.newCurrencyVisible != visible) {
            setState { it.copy(newCurrencyVisible = visible) }
        }
    }

    private fun setBaseCurrencyState(code: String, value: String) {
        if (currentState.baseCurrency != code) {
            setState { it.copy(baseCurrency = code) }
        }
        if (currentState.baseValue != value) {
            setState { it.copy(baseValue = value.toIntOrNull()?.toString() ?: "") }
            val valueLong = value.toLongOrNull() ?: 0
            if (valueLong == 0L) {
                val newCurrencies = currentState.currencies.map { it.copy(second = 0L) }
                setState { it.copy(currencies = newCurrencies) }
            } else {
                convert(valueLong)
            }
        }
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
        setNewCurrencyState("", false)
        if (code.isBlank()) return
        launchScope {
            addCurrencyUseCase(code.take(3).uppercase())
                .onSuccess {}
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun deleteCurrency(code: String) {
        logd(TAG, "Delete currency: code = $code")
        if (code.isBlank()) return
        launchScope {
            if (currentState.baseCurrency == code) {
                setState { it.copy(baseCurrency = "") }
            }
            deleteCurrencyUseCase(code)
                .onSuccess {}
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun convert(baseValue: Long) {
        if (convertJob?.isActive == true) convertJob?.cancel()
        convertJob = launchScope {
            delay(200)
            val currencies = currentState.currencies
            val exchanges = currentState.exchanges
            val baseCurrency = currentState.baseCurrency
            logd(TAG, "Convert value: base = $baseCurrency, value = $baseValue")
            convertCurrencyUseCase.invoke(currencies, exchanges, baseCurrency, baseValue)
                .onSuccess { result -> setState { it.copy(currencies = result) } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private suspend fun setCurrenciesFlow(flow: Flow<List<CurrencyEntity>>) {
        flow.collectLatest { list ->
            logd(TAG, "Collecting currencies: $list")
            val currencies = list.map { Pair(it.code, 0L) }
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
