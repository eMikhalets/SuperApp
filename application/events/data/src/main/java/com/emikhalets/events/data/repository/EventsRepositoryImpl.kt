package com.emikhalets.events.data.repository

import android.net.Uri
import com.emikhalets.events.data.database.table.alarm.AlarmsDao
import com.emikhalets.events.data.database.table.events.EventsDao
import com.emikhalets.events.data.database.table.groups.GroupsDao
import com.emikhalets.events.data.mapper.AlarmMapper
import com.emikhalets.events.data.mapper.EventMapper
import com.emikhalets.events.data.mapper.GroupMapper
import com.emikhalets.events.domain.entity.AlarmEntity
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.entity.GroupEntity
import com.emikhalets.events.domain.repository.EventsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val eventsDao: EventsDao,
    private val eventAlarmsDao: AlarmsDao,
    private val groupsDao: GroupsDao,
//    private val backupManager: AppBackupManager,
) : EventsRepository {

    override suspend fun importEvents(uri: Uri): Result<List<EventEntity>> {
        return Result.success(emptyList())
//        return runCatching { backupManager.import(uri) }
//            .onFailure { it.printStackTrace() }
    }

    override suspend fun exportEvents(uri: Uri, events: List<EventEntity>): Result<Boolean> {
        return Result.success(false)
//        return runCatching { backupManager.export(uri, events) }
//            .onFailure { it.printStackTrace() }
    }

    override suspend fun insertEvent(entity: EventEntity): Result<Long> {
        return runCatching {
            val dbEntity = EventMapper.mapEntityToDb(entity)
            eventsDao.insert(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun updateEvent(entity: EventEntity): Result<Int> {
        return runCatching {
            val dbEntity = EventMapper.mapEntityToDb(entity)
            eventsDao.update(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun deleteEvent(entity: EventEntity): Result<Int> {
        return runCatching {
            val dbEntity = EventMapper.mapEntityToDb(entity)
            eventsDao.delete(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun getAllEventsFlow(): Result<Flow<List<EventEntity>>> {
        return runCatching {
            eventsDao.getAllEntities().map {
                EventMapper.mapDbListToList(it)
            }
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun getEventById(eventId: Long): Result<EventEntity> {
        return runCatching {
            val dbEntity = eventsDao.getEntityById(eventId)
            EventMapper.mapDbToEntity(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun getEventsByGroup(entity: GroupEntity): Result<Flow<List<EventEntity>>> {
        return runCatching {
            eventsDao.getAllByGroupId(entity.id).map {
                EventMapper.mapDbListToList(it)
            }
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun insertGroup(entity: GroupEntity): Result<Long> {
        return runCatching {
            val dbEntity = GroupMapper.mapEntityToDb(entity)
            groupsDao.insert(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun updateGroup(entity: GroupEntity): Result<Int> {
        return runCatching {
            val dbEntity = GroupMapper.mapEntityToDb(entity)
            groupsDao.update(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun deleteGroup(entity: GroupEntity): Result<Int> {
        return runCatching {
            val dbEntity = GroupMapper.mapEntityToDb(entity)
            groupsDao.delete(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun getAllGroups(): Result<Flow<List<GroupEntity>>> {
        return runCatching {
            groupsDao.getAllEntities().map {
                GroupMapper.mapDbListToList(it)
            }
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun getGroupById(id: Long): Result<Flow<GroupEntity>> {
        return runCatching {
            groupsDao.getEntityByIdFlow(id).map {
                GroupMapper.mapDbToEntity(it)
            }
        }.onFailure { it.printStackTrace() }
    }
}
