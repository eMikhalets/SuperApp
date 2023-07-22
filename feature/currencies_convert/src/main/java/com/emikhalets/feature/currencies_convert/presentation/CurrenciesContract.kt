package com.emikhalets.feature.currencies_convert.presentation

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.feature.currencies_convert.domain.model.ExchangeModel

object CurrenciesContract {

    @Immutable
    sealed class Action : UiAction {

        object DropError : Action()
        class AddCurrency(val code: String) : Action()
        class DeleteCurrency(val code: String) : Action()
        class NewCurrencyEvent(val code: String, val visible: Boolean) : Action()
        class BaseCurrencyEvent(val code: String, val value: String) : Action()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val exchanges: List<ExchangeModel> = emptyList(),
        val currencies: List<Pair<String, Long>> = emptyList(),
        val isOldExchanges: Boolean = false,
        val newCurrencyVisible: Boolean = false,
        val newCurrencyCode: String = "",
        val baseCurrency: String = "",
        val baseValue: String = "",
        val date: Long = 0,
        val error: UiString? = null,
    ) : UiState
}
