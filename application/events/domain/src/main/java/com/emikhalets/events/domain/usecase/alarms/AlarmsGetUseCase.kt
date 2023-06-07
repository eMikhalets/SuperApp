package com.emikhalets.events.domain.usecase.alarms

import com.emikhalets.common.AppResult
import com.emikhalets.events.domain.entity.AlarmEntity
import com.emikhalets.events.domain.repository.AlarmsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmsGetUseCase @Inject constructor(
    private val alarmsRepo: AlarmsRepository,
) {

    suspend operator fun invoke(): AppResult<Flow<List<AlarmEntity>>> {
        return alarmsRepo.getAllAlarmsFlow()
    }
}
