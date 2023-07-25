package com.emikhalets.feature.workout.domain

import com.emikhalets.feature.workout.data.Repository
import com.emikhalets.feature.workout.domain.model.ProgramModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetProgramUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(id: Long): Flow<ProgramModel> {
        return repository.getProgram(id)
    }
}
