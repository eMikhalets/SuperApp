package com.emikhalets.convert.presentation.screens

import androidx.compose.runtime.Immutable
import com.emikhalets.convert.domain.model.ExchangeModel
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.core.ui.StringValue

object CurrenciesContract {

    @Immutable
    sealed class Action : UiAction {

        object AddCurrency : Action()
        object DropError : Action()
        class DeleteCurrency(val code: String) : Action()

        sealed class Input : Action() {
            class BaseCodeClick(val code: String) : Input()
            class BaseValueChange(val value: String) : Input()
            class NewCurrencyChange(val value: String) : Input()
            class NewCurrencyVisible(val visible: Boolean) : Input()
        }
    }

    @Immutable
    sealed class Effect : UiEffect

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val exchanges: List<ExchangeModel> = emptyList(),
        val currencies: List<Pair<String, Long>> = emptyList(),
        val isOldExchanges: Boolean = false,
        val newCurrencyVisible: Boolean = false,
        val newCurrencyCode: String = "",
        val baseCode: String = "",
        val baseValue: String = "",
        val date: Long = 0,
        val error: StringValue? = null,
    ) : UiState
}
