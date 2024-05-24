package com.emikhalets.superapp.domain.notes.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.domain.notes.model.TaskModel
import com.emikhalets.superapp.domain.notes.repository.NotesRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {

    suspend operator fun invoke(id: Long): Result {
        val deleteError = R.string.common_error_delete
        return when (val result = notesRepository.getTask(id)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(deleteError))
            is AppResult.Success -> Result.Success(result.data)
        }
    }

    sealed interface Result {
        data class Failure(val message: StringValue) : Result
        data class Success(val model: TaskModel) : Result
    }
}