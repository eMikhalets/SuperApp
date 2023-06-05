package com.emikhalets.simplenotes.domain.use_cases.tasks

import com.emikhalets.simplenotes.domain.AppRepository
import com.emikhalets.simplenotes.domain.entities.SubtaskEntity
import com.emikhalets.simplenotes.domain.entities.TaskEntity
import javax.inject.Inject

class UpdateSubtaskUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(entity: SubtaskEntity) = repository.updateSubtask(entity)
}