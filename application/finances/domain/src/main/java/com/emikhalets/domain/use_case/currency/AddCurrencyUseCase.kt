package com.emikhalets.domain.use_case.currency

import com.emikhalets.domain.entity.CurrencyEntity
import com.emikhalets.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddCurrencyUseCase @Inject constructor(
    private val repository: DatabaseRepository,
) {

    suspend operator fun invoke(entity: CurrencyEntity) = repository.insertCurrency(entity)
}