package com.emikhalets.events.domain.usecase.events

import com.emikhalets.common.AppResult
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class EventsInsertUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(entity: EventEntity): AppResult<Long> {
        return repository.insertEvent(entity)
    }

    suspend operator fun invoke(entities: List<EventEntity>): AppResult<List<Long>> {
        return repository.insertEvents(entities)
    }
}
