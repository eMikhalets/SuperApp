package com.emikhalets.events.domain.use_case.alarms

import com.emikhalets.events.domain.entity.AlarmEntity
import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke(entity: AlarmEntity) = when (entity.id) {
        0L -> repository.insertEventAlarm(entity)
        else -> repository.updateEventAlarm(entity)
    }
}
