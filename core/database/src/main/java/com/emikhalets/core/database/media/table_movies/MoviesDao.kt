package com.emikhalets.core.database.media.table_movies

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao : AppDao<MovieDb> {

    @Query("DELETE FROM movies")
    suspend fun drop()

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<MovieDb>

    @Query("SELECT * FROM movies")
    fun getAllFlow(): Flow<List<MovieDb>>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getItem(id: Long): MovieDb

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getItemFlow(id: Long): Flow<MovieDb>
}