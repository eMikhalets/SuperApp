package com.emikhalets.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.emikhalets.core.common.InvalidId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun DataStore<Preferences>.retrieve(key: String): Flow<Long> {
    return data.map { it[longPreferencesKey(key)] ?: InvalidId }
}

suspend fun DataStore<Preferences>.store(key: String, value: Long) {
    edit { it[longPreferencesKey(key)] = value }
}