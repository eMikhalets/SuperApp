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
        object GetExchanges : Action()
        object NewCurrencyShow : Action()
        class AddCurrency(val code: String) : Action()
        class DeleteCurrency(val code: String) : Action()
        class NewCurrencyCode(val code: String) : Action()
        class Convert(val value: Double) : Action()
        class SetBase(val code: String) : Action()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val isOldValues: Boolean = false,
        val isNewCurrencyVisible: Boolean = false,
        val newCurrencyCode: String = "",
        val exchanges: List<ExchangeEntity> = emptyList(),
        val currencies: Map<String, Double> = emptyMap(),
        val date: Long = 0,
        val baseCurrency: String = "",
        val baseValue: Double = 0.0,
        val error: UiString? = null,
    ) : UiState
}
