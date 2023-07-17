package com.emikhalets.events.domain.use_case.groups

import com.emikhalets.events.domain.entity.GroupEntity
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(entity: GroupEntity) = repository.deleteGroup(entity)
}
