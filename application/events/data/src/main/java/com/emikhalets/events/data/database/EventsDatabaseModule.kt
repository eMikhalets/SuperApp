package com.emikhalets.events.data.database

import android.content.Context
import com.emikhalets.events.data.database.table.alarm.AlarmsDao
import com.emikhalets.events.data.database.table.events.EventsDao
import com.emikhalets.events.data.database.table.groups.GroupsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object EventsDatabaseModule {

    @Provides
    fun provideFitnessDatabase(@ApplicationContext context: Context): EventsDatabase {
        return EventsDatabase.getInstance(context)
    }

    @Provides
    fun providesEventsDao(database: EventsDatabase): EventsDao {
        return database.eventsDao
    }

    @Provides
    fun providesEventAlarmsDao(database: EventsDatabase): AlarmsDao {
        return database.alarmsDao
    }

    @Provides
    fun providesGroupsDao(database: EventsDatabase): GroupsDao {
        return database.groupsDao
    }
}
