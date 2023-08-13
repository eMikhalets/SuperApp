package com.emikhalets.convert.presentation.screens

import com.emikhalets.convert.domain.model.CurrencyModel
import com.emikhalets.convert.domain.model.ExchangeModel
import com.emikhalets.convert.domain.use_case.AddCurrencyUseCase
import com.emikhalets.convert.domain.use_case.ConvertCurrencyUseCase
import com.emikhalets.convert.domain.use_case.DeleteCurrencyUseCase
import com.emikhalets.convert.domain.use_case.GetCurrenciesUseCase
import com.emikhalets.convert.domain.use_case.GetExchangesDateUseCase
import com.emikhalets.convert.domain.use_case.GetExchangesUseCase
import com.emikhalets.convert.presentation.screens.CurrenciesContract.Action
import com.emikhalets.convert.presentation.screens.CurrenciesContract.Effect
import com.emikhalets.convert.presentation.screens.CurrenciesContract.State
import com.emikhalets.core.common.LongZero
import com.emikhalets.core.common.StringEmpty
import com.emikhalets.core.common.date.startOfNextDay
import com.emikhalets.core.common.mvi.MviViewModel
import com.emikhalets.core.common.mvi.launch
import com.emikhalets.core.ui.StringValue
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
        launch {
            getExchangesDateUseCase()
                .catch { setFailureState(it) }
                .collectLatest { setExchangesDateState(it) }
            getCurrenciesUseCase()
                .catch { setFailureState(it) }
                .collectLatest { setCurrenciesState(it) }
            getExchangesUseCase()
                .onEach { setState { it.copy(isLoading = true) } }
                .catch { setFailureState(it) }
                .collectLatest { setExchangesState(it) }
        }
    }

    override fun setInitialState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            Action.AddCurrency -> addCurrency()
            Action.DropError -> dropError()
            is Action.DeleteCurrency -> deleteCurrency(action.code)
            is Action.Input -> {
                when (action) {
                    is Action.Input.BaseCurrencyClick -> setBaseCode(action.code)
                    is Action.Input.BaseCurrencyChange -> setBaseValue(action.value)
                    is Action.Input.NewCurrencyChange -> setNewCurrencyCode(action.value)
                    is Action.Input.NewCurrencyVisible -> setNewCurrencyVisible(action.visible)
                }
            }
        }
    }

    override fun handleError(message: String?) {
        setState { it.copy(error = StringValue.create(message)) }
    }

    private fun dropError() {
        setState { it.copy(error = null) }
    }

    private fun setNewCurrencyCode(code: String) {
        if (currentState.newCurrencyCode != code) {
            setState { it.copy(newCurrencyCode = code) }
        }
    }

    private fun setNewCurrencyVisible(visible: Boolean) {
        if (currentState.newCurrencyVisible != visible) {
            setState { it.copy(newCurrencyVisible = visible) }
        }
    }

    private fun setBaseCode(code: String) {
        if (currentState.baseCode != code) {
            setState { it.copy(baseCode = code) }
        }
    }

    private fun setBaseValue(value: String) {
        if (currentState.baseValue != value) {
            val valueValidated = value.toLongOrNull()?.toString() ?: StringEmpty
            setState { it.copy(baseValue = valueValidated) }
            valueValidated.toLongOrNull()?.let {
                convert(it)
            } ?: run {
                val newCurrencies = currentState.currencies.map { it.copy(second = LongZero) }
                setState { it.copy(currencies = newCurrencies) }
            }
        }
    }

    private fun addCurrency() {
        launch {
            val code = currentState.newCurrencyCode
            if (code.isNotBlank()) {
                addCurrencyUseCase(code.take(3).uppercase())
            }
            setState { it.copy(newCurrencyCode = "", newCurrencyVisible = false) }
        }
    }

    private fun deleteCurrency(code: String) {
        launch {
            if (code.isNotBlank()) {
                if (currentState.baseCode == code) {
                    setState { it.copy(baseCode = StringEmpty) }
                }
                deleteCurrencyUseCase(code)
            }
        }
    }

    private fun convert(baseValue: Long) {
        if (convertJob?.isActive == true) convertJob?.cancel()
        convertJob = launch {
            delay(200)
            val currencies = currentState.currencies
            val exchanges = currentState.exchanges
            val baseCode = currentState.baseCode
            val newList = convertCurrencyUseCase(currencies, exchanges, baseCode, baseValue)
            setState { it.copy(currencies = newList) }
        }
    }

    private fun setCurrenciesState(list: List<CurrencyModel>) {
        val currencies = list.map { Pair(it.code, LongZero) }
        setState { it.copy(currencies = currencies) }
    }

    private fun setExchangesState(list: List<ExchangeModel>) {
        setState { it.copy(exchanges = list, isLoading = false) }
    }

    private fun setExchangesDateState(date: Long) {
        val hasOldExchanges = date.startOfNextDay() < Date().time
        setState { it.copy(date = date, isOldExchanges = hasOldExchanges) }
    }

    private fun setFailureState(throwable: Throwable) {
        setState { it.copy(isLoading = false, error = StringValue.create(throwable)) }
    }
}
