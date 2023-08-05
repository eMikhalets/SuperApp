package com.emikhalets.feature.currencies_convert.presentation

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.date.startOfNextDay
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.MviViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.toIntOrNull
import com.emikhalets.feature.currencies_convert.domain.model.CurrencyModel
import com.emikhalets.feature.currencies_convert.domain.model.ExchangeModel
import com.emikhalets.feature.currencies_convert.domain.use_case.AddCurrencyUseCase
import com.emikhalets.feature.currencies_convert.domain.use_case.ConvertCurrencyUseCase
import com.emikhalets.feature.currencies_convert.domain.use_case.DeleteCurrencyUseCase
import com.emikhalets.feature.currencies_convert.domain.use_case.GetCurrenciesUseCase
import com.emikhalets.feature.currencies_convert.domain.use_case.GetExchangesDateUseCase
import com.emikhalets.feature.currencies_convert.domain.use_case.GetExchangesUseCase
import com.emikhalets.feature.currencies_convert.presentation.CurrenciesContract.Action
import com.emikhalets.feature.currencies_convert.presentation.CurrenciesContract.Effect
import com.emikhalets.feature.currencies_convert.presentation.CurrenciesContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getExchangesUseCase: GetExchangesUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val addCurrencyUseCase: AddCurrencyUseCase,
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val getExchangesDateUseCase: GetExchangesDateUseCase,
) : MviViewModel<Action, Effect, State>() {

    private var convertJob: Job? = null

    init {
        launchScope {
            getExchangesDateUseCase()
                .catch { handleFailure(it) }
                .collectLatest { setExchangesDate(it) }
            getCurrenciesUseCase()
                .catch { handleFailure(it) }
                .collectLatest { setCurrenciesList(it) }
            getExchangesUseCase()
                .onEach { setState { it.copy(isLoading = true) } }
                .catch { handleFailure(it) }
                .collectLatest { setExchangesList(it) }
        }
    }

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.DropError -> dropErrorState()
            is Action.AddCurrency -> addCurrency(action.code)
            is Action.DeleteCurrency -> deleteCurrency(action.code)
            is Action.NewCurrencyEvent -> setNewCurrencyState(action.code, action.visible)
            is Action.BaseCurrencyEvent -> setBaseCurrencyState(action.code, action.value)
        }
    }

    override fun handleError(message: String?) {
        setState { it.copy(error = UiString.create(message)) }
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

    private fun addCurrency(code: String) {
        logd(TAG, "Add currency: code = $code")
        launchScope {
            setNewCurrencyState("", false)
            if (code.isBlank()) return@launchScope
            addCurrencyUseCase(code.take(3).uppercase())
        }
    }

    private fun deleteCurrency(code: String) {
        logd(TAG, "Delete currency: code = $code")
        launchScope {
            if (code.isBlank()) return@launchScope
            if (currentState.baseCurrency == code) {
                setState { it.copy(baseCurrency = "") }
            }
            deleteCurrencyUseCase(code)
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
            val newList = convertCurrencyUseCase(currencies, exchanges, baseCurrency, baseValue)
            setState { it.copy(currencies = newList) }
        }
    }

    private fun setCurrenciesList(list: List<CurrencyModel>) {
        logd(TAG, "Collecting currencies: $list")
        val currencies = list.map { Pair(it.code, 0L) }
        setState { it.copy(currencies = currencies) }
    }

    private fun setExchangesList(list: List<ExchangeModel>) {
        logd(TAG, "Collecting exchanges: $list")
        setState { it.copy(exchanges = list, isLoading = false) }
    }

    private fun setExchangesDate(date: Long) {
        logd(TAG, "Collecting date = $date")
        val hasOldExchanges = date.startOfNextDay() < Date().time
        setState { it.copy(date = date, isOldExchanges = hasOldExchanges) }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        logd(TAG, "Handle error: code = $code")
        setState { it.copy(isLoading = false, error = message) }
    }

    private fun handleFailure(throwable: Throwable) {
        logd(TAG, "Handling error: throwable = $throwable")
        setState { it.copy(isLoading = false, error = UiString.create(throwable)) }
    }

    companion object {

        private const val TAG = "CurrenciesVM"
    }
}
