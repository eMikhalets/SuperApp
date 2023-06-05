package com.emikhalets.events.domain.use_case.events

import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(entity: EventEntity) = repository.deleteEvent(entity)
}
