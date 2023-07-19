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
    fun getAllEntities(): Flow<List<EventDb>>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEntityById(id: Long): EventDb

    @Query("SELECT * FROM events WHERE group_id = :id")
    fun getAllByGroupId(id: Long): Flow<List<EventDb>>
}
