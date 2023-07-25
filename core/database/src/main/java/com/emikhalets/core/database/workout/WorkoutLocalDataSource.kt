package com.emikhalets.core.database.workout

import com.emikhalets.core.database.workout.table_programs.ProgramDb
import com.emikhalets.core.database.workout.table_programs.ProgramsDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class WorkoutLocalDataSource @Inject constructor(
    private val programsDao: ProgramsDao,
) {

    fun getPrograms(): Flow<List<ProgramDb>> {
        return programsDao.getAllFlow()
    }

    suspend fun insertProgram(model: ProgramDb): Long {
        return programsDao.insert(model)
    }

    suspend fun deleteProgram(model: ProgramDb) {
        return programsDao.delete(model)
    }
}