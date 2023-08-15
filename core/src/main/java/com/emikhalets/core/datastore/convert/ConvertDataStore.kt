package com.emikhalets.core.datastore.convert

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.emikhalets.core.datastore.retrieveLong
import com.emikhalets.core.datastore.store
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

private const val NAME = "ConvertDataStore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME)

class ConvertDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    @Deprecated("Need to delete")
    fun getExchangesDate(): Flow<Long> {
        return context.dataStore.retrieveLong(EXCHANGES_UPDATE_DATE)
    }

    @Deprecated("Need to delete")
    suspend fun setExchangesDate(value: Long) {
        context.dataStore.store(EXCHANGES_UPDATE_DATE, value)
    }

    companion object {

        @Deprecated("Need to delete")
        private const val EXCHANGES_UPDATE_DATE = "EXCHANGES_UPDATE_DATE"
    }
}