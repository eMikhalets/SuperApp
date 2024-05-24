package com.emikhalets.superapp.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val NAME = "ConvertConfig"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME)

//TODO: сохранил для примера, удалить
class ConvertDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun getExchangesDate(): Flow<Long> {
        return context.dataStore.getLong(EXCHANGES_DATE)
    }

    suspend fun setExchangesDate(value: Long) {
        context.dataStore.put(EXCHANGES_DATE, value)
    }

    companion object {

        private const val EXCHANGES_DATE = "EXCHANGES_DATE"
    }
}