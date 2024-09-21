package com.emikhalets.superapp.feature.convert.ui.currencies

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.ui.mvi.MviAction
import com.emikhalets.superapp.core.ui.mvi.MviEffect
import com.emikhalets.superapp.core.ui.mvi.MviState
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel

object CurrenciesContract {

    @Immutable
    sealed class Action : MviAction {

        data object AddCurrency : Action()
        data object GetExchanges : Action()
        data object UpdateExchanges : Action()
        data class DropTempMessage(val value: StringValue?) : Action()
        data class DropError(val value: StringValue?) : Action()
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
        val exchanges: List<ExchangeModel> = emptyList(),
        val pairs: List<Pair<String, Long>> = emptyList(),
        val codes: List<String> = emptyList(),
        val isOldExchanges: Boolean = false,
        val newCodeVisible: Boolean = false,
        val newCode: String = "",
        val baseCode: String = "",
        val baseValue: String = "",
        val updateDate: Long = 0,
        val tempMessage: StringValue? = null,
        val error: StringValue? = null,
    ) : MviState
}
