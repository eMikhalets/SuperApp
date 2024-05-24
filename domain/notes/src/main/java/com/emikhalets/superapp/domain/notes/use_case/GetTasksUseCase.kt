package com.emikhalets.superapp.domain.notes.use_case

import com.emikhalets.superapp.domain.notes.model.TaskModel
import com.emikhalets.superapp.domain.notes.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {

    operator fun invoke(): Flow<List<TaskModel>> {
        return notesRepository.getTasks()
    }
}