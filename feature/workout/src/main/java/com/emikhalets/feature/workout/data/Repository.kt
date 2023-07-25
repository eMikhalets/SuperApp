package com.emikhalets.feature.workout.data

import com.emikhalets.core.database.notes.NotesLocalDataSource
import com.emikhalets.feature.tasks.domain.model.ProgramModel
import com.emikhalets.feature.tasks.domain.model.ProgramType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository @Inject constructor(
    private val localDataSource: NotesLocalDataSource,
) {

    fun getPrograms(): Flow<List<ProgramModel>> {
        return flowOf(emptyList())
    }

    fun getProgram(id: Long): Flow<ProgramModel> {
        return flowOf(ProgramModel(ProgramType.Dynamic))
    }

    suspend fun insertProgram(model: ProgramModel) {
    }

    suspend fun updateProgram(model: ProgramModel) {
    }

    suspend fun deleteProgram(model: ProgramModel) {
    }
}