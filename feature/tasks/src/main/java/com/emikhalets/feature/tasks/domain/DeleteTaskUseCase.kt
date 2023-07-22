package com.emikhalets.feature.tasks.domain

import com.emikhalets.feature.tasks.data.Repository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(model: TaskModel) {
        repository.deleteTask(model)
    }
}