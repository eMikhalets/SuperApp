package com.emikhalets.core.database.events.table_groups

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupsDao : AppDao<GroupDb> {

    @Query("DELETE FROM groups")
    suspend fun drop()

    @Query("SELECT * FROM groups")
    fun getAllEntities(): Flow<List<GroupDb>>

    @Query("SELECT * FROM groups WHERE id = :id ORDER BY name ASC")
    fun getEntityByIdFlow(id: Long): Flow<GroupDb>

    @Query("SELECT * FROM groups WHERE id = :id")
    suspend fun getEntityById(id: Long): GroupDb
}
