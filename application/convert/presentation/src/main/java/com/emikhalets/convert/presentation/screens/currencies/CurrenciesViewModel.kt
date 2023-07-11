package com.emikhalets.convert.presentation.screens.currencies

import com.emikhalets.convert.domain.ConvertDataStore
import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.convert.domain.usecase.AddCurrencyUseCase
import com.emikhalets.convert.domain.usecase.ConvertCurrencyUseCase
import com.emikhalets.convert.domain.usecase.DeleteCurrencyUseCase
import com.emikhalets.convert.domain.usecase.GetExchangesUseCase
import com.emikhalets.convert.presentation.screens.currencies.CurrenciesContract.Action
import com.emikhalets.convert.presentation.screens.currencies.CurrenciesContract.State
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getExchangesUseCase: GetExchangesUseCase,
    private val addCurrencyUseCase: AddCurrencyUseCase,
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val dataStore: ConvertDataStore,
) : BaseViewModel<Action, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        logd(TAG, "User event: $action")
        when (action) {
            Action.DropError -> dropErrorState()
            Action.GetExchanges -> getExchanges()
            Action.NewCurrencyShow -> setState { it.copy(isNewCurrencyVisible = true) }
            is Action.AddCurrency -> addCurrency(action.code)
            is Action.Convert -> convert(action.value)
            is Action.DeleteCurrency -> deleteCurrency(action.code)
            is Action.NewCurrencyCode -> setState { it.copy(newCurrencyCode = action.code) }
            is Action.SetBase -> setState { it.copy(baseCurrency = action.code) }
        }
    }

    private fun dropErrorState() {
        setState { it.copy(error = null) }
    }

    private fun getExchanges() {
        logd(TAG, "Get exchanges")
        launchScope {
            getExchangesUseCase()
                .onSuccess { flow -> setExchangesFlow(flow) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun addCurrency(code: String) {
        logd(TAG, "Add currency: code = $code")
        launchScope {
            addCurrencyUseCase(code)
                .onSuccess { setState { it.copy(isLoading = false) } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun deleteCurrency(code: String) {
        logd(TAG, "Delete currency: code = $code")
        launchScope {
            deleteCurrencyUseCase(code)
                .onSuccess { setState { it.copy(isLoading = false) } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun convert(value: Double) {
        logd(TAG, "Convert value: base = ${currentState.baseCurrency}, value = $value")
        launchScope {
            convertCurrencyUseCase(value)
                .onSuccess { result ->
                    setState { it.copy(isLoading = false, currencies = result) }
                }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private suspend fun setExchangesFlow(flow: Flow<List<ExchangeEntity>>) {
        flow.collectLatest { list ->
            logd(TAG, "Collecting exchanges list: $list")
            val currencies = mutableMapOf<String, Double>()
            list.forEach { exchange ->
                if (!currencies.containsKey(exchange.mainCurrency)) {
                    currencies[exchange.mainCurrency] = exchange.value
                }
            }
            dataStore.getCurrenciesDate { date ->
                setState {
                    it.copy(
                        isLoading = false,
                        exchanges = list,
                        currencies = currencies,
                        date = date,
                        isOldValues = list.any { item -> item.isOldValue() }
                    )
                }
            }
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
