package com.emikhalets.feature.transactions.presentation

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.feature.transactions.domain.model.TransactionModel

object TransactionsContract {

    @Immutable
    sealed class Action : UiAction {

        object DropError : Action()
        class AddTransaction(val model: TransactionModel) : Action()
        class UpdateTransaction(val model: TransactionModel) : Action()
        class DeleteTransaction(val model: TransactionModel) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val transactions: List<TransactionModel> = emptyList(),
        val error: UiString? = null,
    ) : UiState
}
