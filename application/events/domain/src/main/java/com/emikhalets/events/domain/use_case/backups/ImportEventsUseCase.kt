package com.emikhalets.events.domain.use_case.backups

import android.net.Uri
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class ImportEventsUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(uri: Uri) = repository.importEvents(uri)
}
