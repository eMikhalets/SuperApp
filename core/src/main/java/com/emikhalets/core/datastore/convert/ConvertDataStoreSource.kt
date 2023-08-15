package com.emikhalets.core.datastore.convert

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ConvertPrefsDataSource @Inject constructor(
    private val dataStore: ConvertDataStore,
) {

    @Deprecated("Need to delete")
    fun getExchangesDate(): Flow<Long> {
        return dataStore.getExchangesDate()
    }

    @Deprecated("Need to delete")
    suspend fun setExchangesDate(value: Long) {
        dataStore.setExchangesDate(value)
    }
}