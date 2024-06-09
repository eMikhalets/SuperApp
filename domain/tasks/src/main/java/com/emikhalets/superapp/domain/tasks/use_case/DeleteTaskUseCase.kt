package com.emikhalets.superapp.domain.tasks.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.domain.tasks.TaskModel
import com.emikhalets.superapp.domain.tasks.TasksRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) {

    suspend operator fun invoke(model: TaskModel): Result {
        val deleteError = R.string.common_error_delete
        return when (tasksRepository.deleteTask(model)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(deleteError))
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        data class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}