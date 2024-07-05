package com.emikhalets.superapp.feature.convert.ui.currencies

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.mvi.MviAction
import com.emikhalets.superapp.core.common.mvi.MviEffect
import com.emikhalets.superapp.core.common.mvi.MviState
import com.emikhalets.superapp.domain.convert.CurrencyPairModel

object CurrenciesContract {

    @Immutable
    sealed class Action : MviAction {

        data object AddCurrency : Action()
        data object UpdateExchanges : Action()
        data class DeleteCurrency(val code: String) : Action()
        data class SetBaseCode(val code: String) : Action()
        data class SetBaseValue(val value: String) : Action()
        data class SetNewCurrencyVisible(val visible: Boolean) : Action()
        data class SetNewCurrencyCode(val code: String) : Action()
    }

    @Immutable
    sealed class Effect : MviEffect

    @Immutable
    data class State(
        val loading: Boolean = false,
        val pairList: List<Pair<String, String>> = emptyList(),
        val exchanges: List<CurrencyPairModel> = emptyList(),
        val currencies: List<String> = emptyList(),
        val isOldExchanges: Boolean = false,
        val newCurrencyVisible: Boolean = false,
        val newCurrencyCode: String = "",
        val baseCode: String = "",
        val baseValue: String = "",
        val date: Long = 0,
    ) : MviState
}
