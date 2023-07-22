package com.emikhalets.core.database.finance.table_category

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emikhalets.core.database.AppDao
import com.emikhalets.core.database.finance.embedded.CategoryFullDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao : AppDao<CategoryDb> {

    @Query("DELETE FROM categories")
    suspend fun drop()

    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<CategoryDb>

    @Query("SELECT * FROM categories")
    fun getAllFlow(): Flow<List<CategoryDb>>

    @Query("SELECT * FROM categories WHERE type=:type")
    fun getAllFlow(type: String): Flow<List<CategoryDb>>

    @Transaction
    @Query("SELECT * FROM categories")
    fun getAllFullFlow(): Flow<List<CategoryFullDb>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getItem(id: Long): CategoryDb

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getItemFlow(id: Long): Flow<CategoryDb>

    @Query("SELECT EXISTS(SELECT * FROM categories WHERE name=:name AND type=:type)")
    suspend fun isNameTypeExists(name: String, type: String): Boolean
}