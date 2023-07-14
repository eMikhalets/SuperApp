package com.emikhalets.convert.presentation.screens.currencies

import androidx.compose.runtime.Immutable
import com.emikhalets.convert.domain.entity.ExchangeEntity
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiState

object CurrenciesContract {

    @Immutable
    sealed class Action : UiAction {

        object DropError : Action()
        object GetCurrencies : Action()
        object GetExchanges : Action()
        class AddCurrency(val code: String) : Action()
        class DeleteCurrency(val code: String) : Action()
        class Convert(val value: String) : Action()
        class NewCurrencyShow(val visible: Boolean) : Action()
        class NewCurrencyCode(val code: String) : Action()
        class SetBaseCurrency(val code: String) : Action()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val exchanges: List<ExchangeEntity> = emptyList(),
        val currencies: List<Pair<String, Double>> = emptyList(),
        val isOldExchanges: Boolean = false,
        val isNewCurrencyVisible: Boolean = false,
        val newCurrencyCode: String = "",
        val date: Long = 0,
        val baseCurrency: String = "",
        val baseValue: String = "0.0",
        val error: UiString? = null,
    ) : UiState
}
