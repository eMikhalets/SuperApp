package com.emikhalets.superapp.feature.notes.domain.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.feature.notes.domain.SubTaskModel
import com.emikhalets.superapp.feature.notes.domain.TasksRepository
import javax.inject.Inject

class DeleteSubTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) {

    suspend operator fun invoke(model: SubTaskModel): Result {
        val deleteError = R.string.error_delete
        return when (tasksRepository.deleteSubTask(model)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(deleteError))
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        data class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}