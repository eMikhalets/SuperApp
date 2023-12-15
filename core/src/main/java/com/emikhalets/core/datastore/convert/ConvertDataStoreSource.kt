package com.emikhalets.core.datastore.convert

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ConvertDataStoreSource @Inject constructor(
    private val dataStore: ConvertDataStore,
) {

    fun getExchangesDate(): Flow<Long> {
        return dataStore.getExchangesDate()
    }

    suspend fun setExchangesDate(value: Long) {
        dataStore.setExchangesDate(value)
    }
}