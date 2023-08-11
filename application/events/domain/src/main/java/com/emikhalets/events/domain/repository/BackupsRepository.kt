package com.emikhalets.events.domain.repository

import android.net.Uri
import com.emikhalets.common.AppResult
import com.emikhalets.events.domain.entity.EventEntity

interface BackupsRepository {

    suspend fun importEvents(uri: Uri): AppResult<List<EventEntity>>

    suspend fun exportEvents(uri: Uri, events: List<EventEntity>): AppResult<Boolean>
}
