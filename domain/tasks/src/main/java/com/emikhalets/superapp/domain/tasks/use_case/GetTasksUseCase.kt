package com.emikhalets.superapp.domain.tasks.use_case

import com.emikhalets.superapp.domain.tasks.TaskModel
import com.emikhalets.superapp.domain.tasks.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) {

    operator fun invoke(): Flow<List<TaskModel>> {
        return tasksRepository.getTasks()
    }
}