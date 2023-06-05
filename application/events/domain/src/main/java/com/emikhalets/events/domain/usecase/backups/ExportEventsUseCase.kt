package com.emikhalets.events.domain.usecase.backups

import android.net.Uri
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class ExportEventsUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(uri: Uri, events: List<EventEntity>) =
        repository.exportEvents(uri, events)
}
