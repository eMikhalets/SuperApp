package com.emikhalets.events.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.events.data.database.table.alarm.AlarmDb
import com.emikhalets.events.data.database.table.alarm.AlarmsDao
import com.emikhalets.events.data.database.table.events.EventDb
import com.emikhalets.events.data.database.table.events.EventsDao
import com.emikhalets.events.data.database.table.groups.GroupDb
import com.emikhalets.events.data.database.table.groups.GroupsDao

@Database(
    entities = [
        EventDb::class,
        AlarmDb::class,
        GroupDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class EventsDatabase : RoomDatabase() {

    abstract val eventsDao: EventsDao
    abstract val alarmsDao: AlarmsDao
    abstract val groupsDao: GroupsDao

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
