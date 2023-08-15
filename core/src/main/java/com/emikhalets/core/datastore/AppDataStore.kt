package com.emikhalets.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.emikhalets.core.common.InvalidId
import com.emikhalets.core.common.InvalidInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun DataStore<Preferences>.retrieveLong(key: String, default: Long = InvalidId): Flow<Long> {
    return data.map { it[longPreferencesKey(key)] ?: default }
}

fun DataStore<Preferences>.retrieveInt(key: String, default: Int = InvalidInt): Flow<Int> {
    return data.map { it[intPreferencesKey(key)] ?: default }
}

fun DataStore<Preferences>.retrieveBool(key: String, default: Boolean = false): Flow<Boolean> {
    return data.map { it[booleanPreferencesKey(key)] ?: default }
}

suspend fun DataStore<Preferences>.store(key: String, value: Long) {
    edit { it[longPreferencesKey(key)] = value }
}

suspend fun DataStore<Preferences>.store(key: String, value: Boolean) {
    edit { it[booleanPreferencesKey(key)] = value }
}

suspend fun DataStore<Preferences>.store(key: String, value: Int) {
    edit { it[intPreferencesKey(key)] = value }
}