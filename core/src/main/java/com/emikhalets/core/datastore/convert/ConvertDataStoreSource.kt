package com.emikhalets.core.datastore.convert

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FinancePrefsDataSource @Inject constructor(
    private val financeDataStore: ConvertDataStore,
) {

    @Deprecated("Need to delete")
    fun getExchangesDate(): Flow<Long> {
        return financeDataStore.getExchangesDate()
    }

    @Deprecated("Need to delete")
    suspend fun setExchangesDate(value: Long) {
        financeDataStore.setExchangesDate(value)
    }
}