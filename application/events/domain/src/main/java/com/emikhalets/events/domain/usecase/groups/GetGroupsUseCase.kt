package com.emikhalets.events.domain.usecase.groups

import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke() = repository.getAllGroups()

    suspend operator fun invoke(id: Long) = repository.getGroupById(id)
}
