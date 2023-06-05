package com.emikhalets.events.domain.use_case.events

import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke() = repository.getAllEvents()

    suspend operator fun invoke(id: Long) = repository.getEventById(id)
}
