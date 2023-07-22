package com.emikhalets.presentation.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.core.UiString
import com.emikhalets.core.getEndOfMonth
import com.emikhalets.core.getStartOfDay
import com.emikhalets.domain.entity.ResultWrapper
import com.emikhalets.domain.entity.TransactionType
import com.emikhalets.domain.entity.complex.ComplexTransactionEntity
import com.emikhalets.domain.entity.utils.TransactionsListEntity
import com.emikhalets.domain.use_case.transaction.GetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsState())
    val state = _state.asStateFlow()

    fun dropError() {
        _state.update { _state.value.setError(null) }
    }

    fun getTransactions(startDate: Long) {
        viewModelScope.launch {
            val endDate = startDate.getEndOfMonth()
            when (val result = getTransactionsUseCase.invoke(startDate, endDate)) {
                is ResultWrapper.Success -> setTransactionsState(result.data)
                is ResultWrapper.Error -> setErrorState(result.message)
            }
        }
    }

    private suspend fun setTransactionsState(flow: Flow<List<ComplexTransactionEntity>>?) {
        flow?.collect { entities ->
            val incomeSum = getListSum(entities, TransactionType.Income)
            val expensesSum = getListSum(entities, TransactionType.Expense)
            val totalSum = incomeSum - expensesSum
            val map = mutableMapOf<Long, List<ComplexTransactionEntity>>()
            entities.forEach { entity ->
                val date = entity.transaction.timestamp.getStartOfDay()
                map[date]?.let { list -> map[date] = list + entity }
                    ?: run { map[date] = listOf(entity) }
            }
            val list = map.map { (key, value) ->
                TransactionsListEntity(
                    timestamp = key,
                    incomeSum = getListSum(value, TransactionType.Income),
                    expensesSum = getListSum(value, TransactionType.Expense),
                    transactions = value
                )
            }
            _state.update {
                _state.value.setTransactions(incomeSum, expensesSum, totalSum, list)
            }
        }
    }

    private fun getListSum(list: List<ComplexTransactionEntity>, type: TransactionType) =
        list.filter { it.transaction.type == type }.sumOf { it.transaction.value }

    private fun setErrorState(message: UiString) {
        _state.update { _state.value.setError(message) }
    }
}