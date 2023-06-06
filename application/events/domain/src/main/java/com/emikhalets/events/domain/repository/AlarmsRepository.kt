package com.emikhalets.events.domain.repository

import com.emikhalets.common.AppResult
import com.emikhalets.events.domain.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

interface AlarmsRepository {

    suspend fun insertAlarm(entity: AlarmEntity): AppResult<Long>

    suspend fun insertAlarms(list: List<AlarmEntity>): AppResult<List<Long>>

    suspend fun updateAlarm(entity: AlarmEntity): AppResult<Int>

    suspend fun deleteAlarm(entity: AlarmEntity): AppResult<Int>

    suspend fun getAllAlarmsFlow(): AppResult<Flow<List<AlarmEntity>>>
}
