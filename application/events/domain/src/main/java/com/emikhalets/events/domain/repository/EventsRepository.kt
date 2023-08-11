package com.emikhalets.events.domain.repository

import com.emikhalets.common.AppResult
import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.entity.GroupEntity
import kotlinx.coroutines.flow.Flow

interface EventsRepository {

    suspend fun insertEvent(entity: EventEntity): AppResult<Long>

    suspend fun insertEvents(entities: List<EventEntity>): AppResult<List<Long>>

    suspend fun updateEvent(entity: EventEntity): AppResult<Int>

    suspend fun deleteEvent(entity: EventEntity): AppResult<Int>

    suspend fun getAllEventsFlow(): AppResult<Flow<List<EventEntity>>>

    suspend fun getEventById(eventId: Long): AppResult<EventEntity>

    suspend fun getEventsByGroup(entity: GroupEntity): AppResult<Flow<List<EventEntity>>>

//    suspend fun insertGroup(entity: GroupEntity): Result<Long>
//    suspend fun updateGroup(entity: GroupEntity): Result<Int>
//    suspend fun deleteGroup(entity: GroupEntity): Result<Int>
//    suspend fun getAllGroups(): Result<Flow<List<GroupEntity>>>
//    suspend fun getGroupById(id: Long): Result<Flow<GroupEntity>>
}
