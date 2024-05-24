package com.emikhalets.superapp.data.salary

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.superapp.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface SalariesDao : AppDao<SalaryDb> {

    @Query("DELETE FROM salaries")
    suspend fun drop()

    @Query("SELECT 1 FROM salaries WHERE id=:id")
    suspend fun getItem(id: Long): SalaryDb

    @Query("SELECT * FROM salaries")
    fun getAllFlow(): Flow<List<SalaryDb>>
}