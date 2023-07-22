package com.emikhalets.core.datastore

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FinancePrefsDataSource @Inject constructor(
    private val financeDataStore: FinanceDataStore,
) {

    fun getExchangesDate(): Flow<Long> {
        return financeDataStore.getExchangesDate()
    }

    suspend fun setExchangesDate(value: Long) {
        financeDataStore.setExchangesDate(value)
    }
}