package com.emikhalets.superapp.domain.notes.use_case

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.domain.notes.model.TaskModel
import com.emikhalets.superapp.domain.notes.repository.NotesRepository
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {

    suspend operator fun invoke(model: TaskModel): Result {
        val insertError = R.string.common_error_insert
        return when (notesRepository.insertTask(model)) {
            is AppResult.Failure -> Result.Failure(StringValue.resource(insertError))
            is AppResult.Success -> Result.Success
        }
    }

    sealed interface Result {
        data class Failure(val message: StringValue) : Result
        data object Success : Result
    }
}