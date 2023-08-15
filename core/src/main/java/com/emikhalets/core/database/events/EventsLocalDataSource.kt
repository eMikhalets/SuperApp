package com.emikhalets.core.database.events

import com.emikhalets.core.database.events.table_alarm.AlarmDb
import com.emikhalets.core.database.events.table_alarm.AlarmsDao
import com.emikhalets.core.database.events.table_events.EventsDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class EventsLocalDataSource @Inject constructor(
    private val alarmsDao: AlarmsDao,
    private val eventsDao: EventsDao,
) {

    fun getCurrencies(): Flow<List<AlarmDb>> {
        return alarmsDao.getAllFlow()
    }
}