package com.emikhalets.feature.workout.data

import com.emikhalets.core.database.workout.WorkoutLocalDataSource
import com.emikhalets.feature.workout.domain.model.ProgramModel
import com.emikhalets.feature.workout.domain.model.ProgramModel.Companion.toDb
import com.emikhalets.feature.workout.domain.model.ProgramModel.Companion.toModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class Repository @Inject constructor(
    private val localDataSource: WorkoutLocalDataSource,
) {

    fun getPrograms(): Flow<List<ProgramModel>> {
        return localDataSource.getPrograms().toModel()
    }

    suspend fun insertProgram(model: ProgramModel) {
        localDataSource.insertProgram(model.toDb())
    }

    suspend fun deleteProgram(model: ProgramModel) {
        localDataSource.deleteProgram(model.toDb())
    }
}