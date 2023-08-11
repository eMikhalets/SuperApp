package com.emikhalets.feature.transactions.presentation

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.MviViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.feature.transactions.domain.model.TransactionModel
import com.emikhalets.feature.transactions.domain.use_case.AddTransactionUseCase
import com.emikhalets.feature.transactions.domain.use_case.DeleteTransactionUseCase
import com.emikhalets.feature.transactions.domain.use_case.GetTransactionsUseCase
import com.emikhalets.feature.transactions.domain.use_case.UpdateTransactionUseCase
import com.emikhalets.feature.transactions.presentation.TransactionsContract.Action
import com.emikhalets.feature.transactions.presentation.TransactionsContract.Effect
import com.emikhalets.feature.transactions.presentation.TransactionsContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) : MviViewModel<Action, Effect, State>() {

    private var convertJob: Job? = null

    init {
        launchScope {
            getTransactionsUseCase()
                .catch { handleFailure(it) }
                .collectLatest { setTransactionsDate(it) }
        }
    }

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.DropError -> dropErrorState()
            is Action.AddTransaction -> addTransaction(action.model)
            is Action.UpdateTransaction -> updateTransactions(action.model)
            is Action.DeleteTransaction -> deleteTransaction(action.model)
        }
    }

    override fun handleError(message: String?) {
        setState { it.copy(error = UiString.create(message)) }
    }

    private fun dropErrorState() {
        setState { it.copy(error = null) }
    }

    private fun addTransaction(model: TransactionModel) {
        launchScope {
            addTransactionUseCase(model)
        }
    }

    private fun deleteTransaction(model: TransactionModel) {
        launchScope {
            deleteTransactionUseCase(model)
        }
    }

    private fun updateTransactions(model: TransactionModel) {
        launchScope {
            updateTransactionUseCase(model)
        }
    }

    private fun setTransactionsDate(list: List<TransactionModel>) {
        setState { it.copy(transactions = list) }
    }

    private fun handleFailure(throwable: Throwable) {
        setState { it.copy(isLoading = false, error = UiString.create(throwable)) }
    }

    companion object {

        private const val TAG = "CurrenciesVM"
    }
}
