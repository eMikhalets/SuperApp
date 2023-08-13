package com.emikhalets.core.datastore.convert

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FinancePrefsDataSource @Inject constructor(
    private val financeDataStore: ConvertDataStore,
) {

    fun getExchangesDate(): Flow<Long> {
        return financeDataStore.getExchangesDate()
    }

    suspend fun setExchangesDate(value: Long) {
        financeDataStore.setExchangesDate(value)
    }
}