package com.emikhalets.convert.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.emikhalets.core.datastore.retrieve
import com.emikhalets.core.datastore.store
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val NAME = "AppConvertData"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME)

class ConvertDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    suspend fun getCurrenciesDate(block: (Long) -> Unit) {
        context.dataStore.retrieve(CURRENCIES_DATE, block)
    }

    suspend fun setCurrenciesDate(value: Long) {
        context.dataStore.store(CURRENCIES_DATE, value)
    }

    companion object {

        private const val CURRENCIES_DATE = "CURRENCIES_DATE"
    }
}