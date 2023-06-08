package com.emikhalets.events.domain.usecase.events

import com.emikhalets.common.AppResult
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class EventsDeleteUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(entity: EventEntity): AppResult<Int> {
        return repository.deleteEvent(entity)
    }
}
