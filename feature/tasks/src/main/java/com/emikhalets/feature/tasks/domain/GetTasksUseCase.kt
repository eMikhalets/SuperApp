package com.emikhalets.feature.tasks.domain

import com.emikhalets.feature.tasks.data.Repository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<List<TaskModel>> {
        return repository.getTasks()
    }
}