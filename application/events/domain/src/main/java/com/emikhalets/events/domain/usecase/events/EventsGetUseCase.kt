package com.emikhalets.events.domain.usecase.events

import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.repository.EventsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventsGetUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(): Result<Flow<List<EventEntity>>> {
        return repository.getAllEvents()
    }

    suspend operator fun invoke(id: Long): Result<EventEntity> {
        return repository.getEventById(id)
    }

    suspend operator fun invoke(query: String, list: List<EventEntity>): List<EventEntity> {
        delay(500)
        return if (query.isEmpty()) {
            list
        } else {
            list.filter { it.name.lowercase().contains(query) }
        }
    }
}
