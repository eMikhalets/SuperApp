package com.emikhalets.events.data.database.table.alarm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmsDao {

    @Insert
    suspend fun insert(entity: AlarmDb): Long

    @Insert
    suspend fun insert(list: List<AlarmDb>): List<Long>

    @Update
    suspend fun update(entity: AlarmDb): Int

    @Delete
    suspend fun delete(entity: AlarmDb): Int

    @Query("SELECT * FROM alarms")
    suspend fun getAll(): List<AlarmDb>

    @Query("SELECT * FROM alarms ORDER BY milliseconds ASC")
    fun getAllFlow(): Flow<List<AlarmDb>>

    @Query("SELECT EXISTS (SELECT * FROM alarms WHERE milliseconds = :milliseconds)")
    fun isAlarmExist(milliseconds: Long): Boolean

    @Query("DELETE FROM alarms")
    suspend fun drop()
}
