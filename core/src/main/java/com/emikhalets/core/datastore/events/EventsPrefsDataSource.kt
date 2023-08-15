package com.emikhalets.core.datastore.events

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class EventsPrefsDataSource @Inject constructor(
    private val dataStore: EventsDataStore,
) {

    fun getAlarmHour(default: Int): Flow<Int> {
        return dataStore.getAlarmHour(default)
    }

    suspend fun setAlarmHour(value: Int) {
        dataStore.setAlarmHour(value)
    }

    fun getAlarmMinute(default: Int): Flow<Int> {
        return dataStore.getAlarmMinute(default)
    }

    suspend fun setAlarmMinute(value: Int) {
        dataStore.setAlarmMinute(value)
    }

    fun getAlarmsEnabled(): Flow<Boolean?> {
        return dataStore.getAlarmsEnabled()
    }

    suspend fun setAlarmsEnabled(value: Boolean) {
        dataStore.setAlarmsEnabled(value)
    }

    fun getAlarmsCreated(): Flow<Boolean?> {
        return dataStore.getAlarmsCreated()
    }

    suspend fun setAlarmsCreated(value: Boolean) {
        dataStore.setAlarmsCreated(value)
    }
}