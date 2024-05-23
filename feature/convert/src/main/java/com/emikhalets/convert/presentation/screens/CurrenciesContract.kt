package com.emikhalets.convert.presentation.screens

import androidx.compose.runtime.Immutable
import com.emikhalets.convert.domain.model.ExchangeModel
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState

object CurrenciesContract {

    @Immutable
    sealed class Action : UiAction {

        data object AddCurrency : Action()
        data object UpdateExchanges : Action()
        data class DeleteCurrency(val code: String) : Action()
        data class SetBaseCode(val code: String) : Action()
        data class SetBaseValue(val value: String) : Action()
        data class SetNewCurrencyVisible(val visible: Boolean) : Action()
        data class SetNewCurrencyCode(val code: String) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect

    @Immutable
    data class State(
        val loading: Boolean = false,
        val pairList: List<Pair<String, String>> = emptyList(),
        val exchanges: List<ExchangeModel> = emptyList(),
        val currencies: List<String> = emptyList(),
        val isOldExchanges: Boolean = false,
        val newCurrencyVisible: Boolean = false,
        val newCurrencyCode: String = "",
        val baseCode: String = "",
        val baseValue: String = "",
        val date: Long = 0,
    ) : UiState
}
