package com.emikhalets.core.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

abstract class AppDataStore(private val context: Context, name: String) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = name)

    protected suspend fun getLong(key: String, block: (Long) -> Unit) =
        context.dataStore.data
            .map { it[longPreferencesKey(key)] ?: -1 }
            .collectLatest { block(it) }

    protected suspend fun setLong(key: String, value: Long) =
        context.dataStore
            .edit { it[longPreferencesKey(key)] = value }
}