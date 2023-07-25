package com.emikhalets.feature.workout.domain

import com.emikhalets.feature.workout.data.Repository
import com.emikhalets.feature.workout.domain.model.ProgramModel
import javax.inject.Inject

class AddProgramUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(model: ProgramModel) {
        return repository.insertProgram(model)
    }
}