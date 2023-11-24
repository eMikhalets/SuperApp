package com.emikhalets.core.database.events.table_events

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao : AppDao<EventDb> {

    @Query("DELETE FROM events")
    suspend fun drop()

    @Query("SELECT * FROM events")
    suspend fun getAll(): List<EventDb>

    @Query("SELECT * FROM events")
    fun getAllFlow(): Flow<List<EventDb>>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getItem(id: Long): EventDb

    @Query("SELECT * FROM events WHERE id = :id")
    fun getItemFlow(id: Long): Flow<EventDb>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getAllByGroupId(id: Long): Flow<List<EventDb>>
}
