package com.emikhalets.events.domain.usecase.groups

import com.emikhalets.events.domain.entity.GroupEntity
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(entity: GroupEntity) = when (entity.id) {
        0L -> repository.insertGroup(entity)
        else -> repository.updateGroup(entity)
    }
}
