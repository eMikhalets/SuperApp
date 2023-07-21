package com.emikhalets.core.database.media.table_genres

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao : AppDao<GenreDb> {

    @Query("DELETE FROM genres")
    suspend fun drop()

    @Query("SELECT * FROM genres")
    suspend fun getAll(): List<GenreDb>

    @Query("SELECT * FROM genres")
    fun getAllFlow(): Flow<List<GenreDb>>

    @Query("SELECT * FROM genres WHERE name = :id")
    suspend fun getItem(id: Long): GenreDb

    @Query("SELECT * FROM genres WHERE name = :id")
    fun getItemFlow(id: Long): Flow<GenreDb>
}