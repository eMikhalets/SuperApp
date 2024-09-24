package com.emikhalets.superapp.feature.convert.ui.currencies

import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.ui.extentions.launch
import com.emikhalets.superapp.core.ui.mvi.MviViewModel
import com.emikhalets.superapp.feature.convert.R
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import com.emikhalets.superapp.feature.convert.domain.buildCodesList
import com.emikhalets.superapp.feature.convert.domain.use_case.ConvertCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.DeleteCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.GetExchangesUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.InsertCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.UpdateExchangesUseCase
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesContract.Action
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesContract.Effect
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getExchangesUseCase: GetExchangesUseCase,
    private val insertCurrencyUseCase: InsertCurrencyUseCase,
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val updateExchangesUseCase: UpdateExchangesUseCase,
) : MviViewModel<Action, Effect, State>() {

    init {
        setAction(Action.GetExchanges)
    }

    override fun setInitialState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            Action.AddCurrency -> addCurrency()
            Action.GetExchanges -> getExchanges()
            Action.UpdateExchanges -> updateExchanges()
            is Action.DropError -> dropError(action.value)
            is Action.DropTempMessage -> dropTempMessage(action.value)
            is Action.DeleteCurrency -> deleteCurrency(action.code)
            is Action.SetBaseCode -> setBaseCode(action.code)
            is Action.SetBaseValue -> setBaseValue(action.value)
            is Action.SetNewCurrencyCode -> setNewCurrencyCode(action.code)
            is Action.SetNewCurrencyVisible -> setNewCurrencyVisible(action.visible)
        }
    }

    private fun dropTempMessage(value: StringValue?) {
        setState { it.copy(tempMessage = value) }
    }

    private fun dropError(value: StringValue?) {
        setState { it.copy(error = value) }
    }

    private fun setNewCurrencyCode(code: String) {
        if (currentState.newCode != code) {
            setState { it.copy(newCode = code.uppercase()) }
        }
    }

    private fun setNewCurrencyVisible(visible: Boolean) {
        if (currentState.newCodeVisible != visible) {
            setState { it.copy(newCodeVisible = visible, newCode = "") }
        }
    }

    private fun setBaseCode(code: String) {
        if (currentState.baseCode != code) {
            setState { it.copy(baseCode = code, baseValue = 0) }
            convert(currentState.baseValue)
        }
    }

    private fun setBaseValue(value: Long) {
        if (currentState.baseValue != value) {
            setState { it.copy(baseValue = value) }
            convert(value)
        }
    }

    private fun getExchanges() {
        launch {
            getExchangesUseCase.invoke()
                .catch { setFailureState(it) }
                .collect { setExchangesState(it) }
        }
    }

    private fun updateExchanges() {
        launch {
            setState { it.copy(loading = true) }
            when (val result = updateExchangesUseCase.invoke(currentState.exchanges)) {
                is UpdateExchangesUseCase.Result.Error -> {
                    setFailureState(result.value)
                }

                UpdateExchangesUseCase.Result.NotUpdated -> {
                    val message = StringValue.resource(R.string.convert_exchanges_not_updated)
                    setState { it.copy(tempMessage = message) }
                }

                UpdateExchangesUseCase.Result.Success -> {
                    Unit
                }
            }
        }
    }

    private fun addCurrency() {
        launch {
            when (val result = insertCurrencyUseCase.invoke(currentState.newCode)) {
                is InsertCurrencyUseCase.Result.Error -> {
                    setState { it.copy(newCode = "", newCodeVisible = false) }
                    setFailureState(result.value)
                }

                is InsertCurrencyUseCase.Result.Validation -> {
                    setState { it.copy(tempMessage = result.value) }
                }

                InsertCurrencyUseCase.Result.NotUpdated -> {
                    val message = StringValue.resource(R.string.convert_exchanges_not_updated)
                    setState { it.copy(tempMessage = message) }
                }

                InsertCurrencyUseCase.Result.Exist -> {
                    val message = StringValue.resource(R.string.convert_new_code_exist)
                    setState { it.copy(tempMessage = message) }
                }

                InsertCurrencyUseCase.Result.Success -> {
                    setState { it.copy(newCode = "", newCodeVisible = false) }
                }
            }
        }
    }

    private fun deleteCurrency(code: String) {
        launch {
            if (currentState.baseCode == code) {
                setState { it.copy(baseCode = "") }
            }
            when (val result = deleteCurrencyUseCase.invoke(code)) {
                is DeleteCurrencyUseCase.Result.Error -> {
                    setFailureState(result.value)
                }

                DeleteCurrencyUseCase.Result.NotDeleted -> {
                    val message = StringValue.resource(R.string.convert_exchanges_not_updated)
                    setState { it.copy(tempMessage = message) }
                }

                DeleteCurrencyUseCase.Result.NotUpdated -> {
                    val message = StringValue.resource(R.string.convert_exchanges_not_deleted)
                    setState { it.copy(tempMessage = message) }
                }

                DeleteCurrencyUseCase.Result.Success -> {
                    setState { it.copy(newCode = "", newCodeVisible = false) }
                }
            }
        }
    }

    private fun convert(baseValue: Long) {
        launch {
            val result = convertCurrencyUseCase.invoke(
                pairs = currentState.pairs,
                exchanges = currentState.exchanges,
                baseCode = currentState.baseCode,
                baseValue = baseValue
            )
            setState { it.copy(pairs = result) }
        }
    }

    private fun setExchangesState(list: List<ExchangeModel>) {
        val codes = list.buildCodesList()
        val pairs = codes.map { Pair(it, 0L) }
        val updatedDate = list.maxOf { it.updateDate }
        val isOldExchanges = list.any { it.isNeedUpdate() }
        convert(currentState.baseValue)
        setState {
            it.copy(
                exchanges = list,
                pairs = pairs,
                codes = codes,
                updateDate = updatedDate,
                isOldExchanges = isOldExchanges,
                loading = false,
            )
        }
    }

    private fun setFailureState(throwable: Throwable?) {
        setFailureState(StringValue.exception(throwable))
    }

    private fun setFailureState(error: StringValue) {
        setState { it.copy(loading = false, error = error) }
    }
}
