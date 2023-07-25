package com.emikhalets.feature.workout.data

import com.emikhalets.core.database.workout.WorkoutLocalDataSource
import com.emikhalets.feature.workout.domain.model.ProgramModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository @Inject constructor(
    private val localDataSource: WorkoutLocalDataSource,
) {

    fun getPrograms(): Flow<List<ProgramModel>> {
        return localDataSource.getPrograms()
            .map { it.toModelList() }
    }

    suspend fun insertProgram(model: ProgramModel) {
        localDataSource.insertProgram(model.toDb())
    }

    suspend fun deleteProgram(model: ProgramModel) {
        localDataSource.deleteProgram(model.toDb())
    }
}