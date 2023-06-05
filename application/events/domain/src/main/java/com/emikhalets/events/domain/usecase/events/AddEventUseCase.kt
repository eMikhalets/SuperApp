package com.emikhalets.events.domain.usecase.events

import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(entity: EventEntity) = when (entity.id) {
        0L -> repository.insertEvent(entity)
        else -> repository.updateEvent(entity)
    }
}