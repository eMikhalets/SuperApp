package com.emikhalets.feature.transactions.domain.use_case

import com.emikhalets.feature.transactions.data.Repository
import com.emikhalets.feature.transactions.domain.model.TransactionModel
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(model: TransactionModel) {
        return repository.updateTransaction(model)
    }
}