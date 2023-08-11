package com.emikhalets.feature.transactions.data

import com.emikhalets.core.database.finance.FinanceLocalDataSource
import com.emikhalets.core.datastore.FinancePrefsDataSource
import com.emikhalets.core.network.CurrencyRemoteDataSource
import com.emikhalets.feature.currencies_convert.data.toModelFlow
import com.emikhalets.feature.transactions.domain.model.TransactionModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class Repository @Inject constructor(
    private val localDataSource: FinanceLocalDataSource,
    private val remoteDataSource: CurrencyRemoteDataSource,
    private val financePrefsSource: FinancePrefsDataSource,
) {

    fun getTransactions(): Flow<List<TransactionModel>> {
        return localDataSource.getExchanges().toModelFlow()
    }

    suspend fun insertTransaction(model: TransactionModel) {
    }

    suspend fun updateTransaction(model: TransactionModel) {
    }

    suspend fun deleteTransaction(model: TransactionModel) {
    }
}