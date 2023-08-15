package com.emikhalets.core.database.events

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.core.database.events.table_alarm.AlarmDb
import com.emikhalets.core.database.events.table_alarm.AlarmsDao
import com.emikhalets.core.database.events.table_events.EventDb
import com.emikhalets.core.database.events.table_events.EventsDao

@Database(
    entities = [
        AlarmDb::class,
        EventDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class EventsDatabase : RoomDatabase() {

    abstract val alarmsDao: AlarmsDao
    abstract val eventsDao: EventsDao

    companion object {

        @Volatile
        private var instance: EventsDatabase? = null

        fun getInstance(context: Context): EventsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): EventsDatabase {
            return Room
                .databaseBuilder(context, EventsDatabase::class.java, "Events.db")
                .build()
        }
    }
}
