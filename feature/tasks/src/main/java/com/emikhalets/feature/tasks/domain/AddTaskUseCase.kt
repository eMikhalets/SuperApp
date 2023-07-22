package com.emikhalets.feature.tasks.domain

import com.emikhalets.feature.tasks.data.Repository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(model: TaskModel) {
        if (model.id == 0L) repository.insertTask(model)
        else repository.updateTask(model)
    }
}