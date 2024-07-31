package com.emikhalets.superapp.core.database.salary.table_salaries

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.superapp.core.database.AppDao
import kotlinx.coroutines.flow.Flow

@Dao
interface SalariesDao : AppDao<SalaryDb> {

    @Query("DELETE FROM salaries")
    suspend fun drop()

    @Query("SELECT * FROM salaries")
    fun getAllFlow(): Flow<List<SalaryDb>>
}