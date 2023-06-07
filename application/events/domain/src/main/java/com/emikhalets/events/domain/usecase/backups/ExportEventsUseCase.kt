package com.emikhalets.events.domain.usecase.backups

import android.net.Uri
import com.emikhalets.common.AppResult
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.repository.BackupsRepository
import javax.inject.Inject

class ExportEventsUseCase @Inject constructor(
    private val backupsRepo: BackupsRepository,
) {

    suspend operator fun invoke(uri: Uri, events: List<EventEntity>): AppResult<Boolean> {
        return backupsRepo.exportEvents(uri, events)
    }
}
