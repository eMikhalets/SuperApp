package com.emikhalets.events.domain.use_case.alarms

import com.emikhalets.events.domain.repository.EventsRepository
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val repository: EventsRepository,
) {

    suspend operator fun invoke() = repository.getAllEventsAlarm()
}
