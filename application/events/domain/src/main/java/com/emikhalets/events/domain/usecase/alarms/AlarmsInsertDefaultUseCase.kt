package com.emikhalets.events.domain.usecase.alarms

import com.emikhalets.common.AppResult
import com.emikhalets.events.domain.entity.AlarmEntity
import com.emikhalets.events.domain.repository.AlarmsRepository
import javax.inject.Inject

class AlarmsInsertDefaultUseCase @Inject constructor(
    private val alarmsRepo: AlarmsRepository,
) {

    suspend operator fun invoke(): AppResult<List<Long>> {
        val alarms = listOf(
            AlarmEntity(1, true, 30),
            AlarmEntity(2, true, 7),
            AlarmEntity(3, true, 2),
            AlarmEntity(4, true, 1),
            AlarmEntity(5, true, 0),
        )
        return alarmsRepo.insertAlarms(alarms)
    }
}
