package com.emikhalets.events.domain.usecase.events

import com.emikhalets.events.domain.entity.GroupEntity
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class GetEventByGroupUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(entity: GroupEntity) = repository.getEventsByGroup(entity)
}
