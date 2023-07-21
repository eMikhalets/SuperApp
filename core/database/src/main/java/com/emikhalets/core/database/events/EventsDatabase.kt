package com.emikhalets.core.database.events

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.core.common.logi
import com.emikhalets.core.database.events.table_alarm.AlarmsDao
import com.emikhalets.core.database.events.table_alarm.AlarmDb
import com.emikhalets.core.database.events.table_events.EventDb
import com.emikhalets.core.database.events.table_events.EventsDao
import com.emikhalets.core.database.events.table_groups.GroupDb
import com.emikhalets.core.database.events.table_groups.GroupsDao

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

        private const val TAG = "EventsDatabase"

        @Volatile
        private var instance: EventsDatabase? = null

        fun getInstance(context: Context): EventsDatabase {
            logi(TAG, "Get instance")
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): EventsDatabase {
            logi(TAG, "Building database")
            return Room
                .databaseBuilder(context, EventsDatabase::class.java, "Events.db")
                .build()
        }
    }
}
