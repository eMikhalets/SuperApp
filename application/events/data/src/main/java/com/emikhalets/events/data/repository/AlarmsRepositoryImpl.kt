package com.emikhalets.events.data.repository

import com.emikhalets.common.AppResult
import com.emikhalets.common.execute
import com.emikhalets.events.data.database.table.alarm.AlarmsDao
import com.emikhalets.events.data.mapper.AlarmMapper
import com.emikhalets.events.domain.entity.AlarmEntity
import com.emikhalets.events.domain.repository.AlarmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(
    private val dao: AlarmsDao,
) : AlarmsRepository {

    override suspend fun insertAlarm(entity: AlarmEntity): AppResult<Long> {
        return execute {
            val isAlarmExist = dao.isAlarmExist(entity.milliseconds)
            if (isAlarmExist) {
                0
            } else {
                val dbEntity = AlarmMapper.mapEntityToDb(entity)
                dao.insert(dbEntity)
            }
        }
    }

    override suspend fun insertAlarms(list: List<AlarmEntity>): AppResult<List<Long>> {
        return execute {
            val isTableExist = dao.getAll().isNotEmpty()
            if (!isTableExist) {
                emptyList()
            } else {
                val dbList = AlarmMapper.mapListToDbList(list)
                dao.insert(dbList)
            }
        }
    }

    override suspend fun updateAlarm(entity: AlarmEntity): AppResult<Int> {
        return execute {
            val isAlarmExist = dao.isAlarmExist(entity.milliseconds)
            if (isAlarmExist) {
                0
            } else {
                val dbEntity = AlarmMapper.mapEntityToDb(entity)
                dao.update(dbEntity)
            }
        }
    }

    override suspend fun deleteAlarm(entity: AlarmEntity): AppResult<Int> {
        return execute {
            val dbEntity = AlarmMapper.mapEntityToDb(entity)
            dao.delete(dbEntity)
        }
    }

    override suspend fun getAllAlarmsFlow(): AppResult<Flow<List<AlarmEntity>>> {
        return execute {
            dao.getAllFlow().map {
                AlarmMapper.mapDbListToList(it)
            }
        }
    }
}
