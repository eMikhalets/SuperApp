package com.emikhalets.events.domain.usecase.alarms

import com.emikhalets.common.AppResult
import com.emikhalets.events.domain.entity.AlarmEntity
import com.emikhalets.events.domain.repository.AlarmsRepository
import javax.inject.Inject

class AlarmsUpdateUseCase @Inject constructor(
    private val alarmsRepo: AlarmsRepository,
) {

    suspend operator fun invoke(entity: AlarmEntity): AppResult<Int> {
        return alarmsRepo.updateAlarm(entity)
    }
}
