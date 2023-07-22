package com.emikhalets.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

private const val NAME = "FinanceDataStore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME)

class FinanceDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun getExchangesDate(): Flow<Long> {
        return context.dataStore.retrieve(EXCHANGES_UPDATE_DATE)
    }

    suspend fun setExchangesDate(value: Long) {
        context.dataStore.store(EXCHANGES_UPDATE_DATE, value)
    }

    companion object {

        private const val EXCHANGES_UPDATE_DATE = "EXCHANGES_UPDATE_DATE"
    }
}