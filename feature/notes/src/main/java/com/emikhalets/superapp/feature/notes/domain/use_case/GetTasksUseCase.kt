package com.emikhalets.superapp.feature.notes.domain.use_case

import com.emikhalets.superapp.feature.notes.domain.TaskModel
import com.emikhalets.superapp.feature.notes.data.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) {

    operator fun invoke(): Flow<List<TaskModel>> {
        return tasksRepository.getTasks()
    }
}