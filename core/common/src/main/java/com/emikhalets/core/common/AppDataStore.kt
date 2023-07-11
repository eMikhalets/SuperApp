package com.emikhalets.core.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.map

suspend fun DataStore<Preferences>.retrieve(key: String, block: (Long) -> Unit) {
    data.map { it[longPreferencesKey(key)] ?: -1 }.collect { block(it) }
}

suspend fun DataStore<Preferences>.store(key: String, value: Long) {
    edit { it[longPreferencesKey(key)] = value }
}