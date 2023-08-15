package com.emikhalets.core.database.events.table_alarm

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmsDao : AppDao<AlarmDb> {

    @Query("DELETE FROM alarms")
    suspend fun drop()

    @Query("SELECT * FROM alarms")
    suspend fun getAll(): List<AlarmDb>

    @Query("SELECT * FROM alarms")
    fun getAllFlow(): Flow<List<AlarmDb>>

    @Query("SELECT * FROM alarms WHERE id = :id")
    suspend fun getItem(id: Long): AlarmDb

    @Query("SELECT * FROM alarms WHERE id = :id")
    fun getItemFlow(id: Long): Flow<AlarmDb>

    @Query("SELECT EXISTS (SELECT * FROM alarms WHERE name = :name)")
    fun isNameExist(name: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM alarms WHERE milliseconds = :milliseconds)")
    fun isDaysExist(milliseconds: Long): Boolean
}
