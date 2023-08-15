package com.emikhalets.core.datastore.events

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.emikhalets.core.datastore.retrieveBool
import com.emikhalets.core.datastore.retrieveInt
import com.emikhalets.core.datastore.store
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

private const val NAME = "EventsDataStore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME)

class EventsDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun getAlarmHour(default: Int): Flow<Int> {
        return context.dataStore.retrieveInt(ALARM_HOUR, default)
    }

    suspend fun setAlarmHour(value: Int) {
        context.dataStore.store(ALARM_HOUR, value)
    }

    fun getAlarmMinute(default: Int): Flow<Int> {
        return context.dataStore.retrieveInt(ALARM_MINUTE, default)
    }

    suspend fun setAlarmMinute(value: Int) {
        context.dataStore.store(ALARM_MINUTE, value)
    }

    fun getAlarmsEnabled(): Flow<Boolean?> {
        return context.dataStore.retrieveBool(ALARMS_ENABLED)
    }

    suspend fun setAlarmsEnabled(value: Boolean) {
        context.dataStore.store(ALARMS_ENABLED, value)
    }

    fun getAlarmsCreated(): Flow<Boolean?> {
        return context.dataStore.retrieveBool(DEFAULT_ALARMS_CREATED)
    }

    suspend fun setAlarmsCreated(value: Boolean) {
        context.dataStore.store(DEFAULT_ALARMS_CREATED, value)
    }

    companion object {

        private const val ALARM_HOUR = "ALARM_HOUR"
        private const val ALARM_MINUTE = "ALARM_MINUTE"
        private const val ALARMS_ENABLED = "ALARMS_ENABLED"
        private const val DEFAULT_ALARMS_CREATED = "DEFAULT_ALARMS_CREATED"
    }
}