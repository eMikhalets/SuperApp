package com.emikhalets.core.superapp.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun DataStore<Preferences>.getBoolean(key: String, default: Boolean = false): Flow<Boolean> {
    return data.map { it[booleanPreferencesKey(key)] ?: default }
}

suspend fun DataStore<Preferences>.put(key: String, value: Boolean) {
    edit { it[booleanPreferencesKey(key)] = value }
}

fun DataStore<Preferences>.getInt(key: String, default: Int = -1): Flow<Int> {
    return data.map { it[intPreferencesKey(key)] ?: default }
}

suspend fun DataStore<Preferences>.put(key: String, value: Int) {
    edit { it[intPreferencesKey(key)] = value }
}

fun DataStore<Preferences>.getLong(key: String, default: Long = -1): Flow<Long> {
    return data.map { it[longPreferencesKey(key)] ?: default }
}

suspend fun DataStore<Preferences>.put(key: String, value: Long) {
    edit { it[longPreferencesKey(key)] = value }
}

fun DataStore<Preferences>.getString(key: String, default: String = ""): Flow<String> {
    return data.map { it[stringPreferencesKey(key)] ?: default }
}

suspend fun DataStore<Preferences>.put(key: String, value: String) {
    edit { it[stringPreferencesKey(key)] = value }
}