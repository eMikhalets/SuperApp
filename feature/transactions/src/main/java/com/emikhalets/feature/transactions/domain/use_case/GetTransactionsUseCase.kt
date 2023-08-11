package com.emikhalets.feature.transactions.domain.use_case

import com.emikhalets.feature.transactions.data.Repository
import com.emikhalets.feature.transactions.domain.model.TransactionModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<List<TransactionModel>> {
        return repository.getTransactions()
    }
}