package com.emikhalets.notes.data.mappers

import com.emikhalets.core.common.logi
import com.emikhalets.notes.data.database.table_notes.NoteDb
import com.emikhalets.notes.domain.entity.NoteEntity

object NotesMapper {

    private const val TAG = "NotesMapper"

    fun mapDbToEntity(entity: NoteDb): NoteEntity {
        logi(TAG, "Db To Entity: entity = $entity")
        val result = NoteEntity(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )
        logi(TAG, "Result = $result")
        return result
    }

    fun mapDbListToEntityList(list: List<NoteDb>): List<NoteEntity> {
        return list.map { mapDbToEntity(it) }
    }

    fun mapEntityToDb(entity: NoteEntity): NoteDb {
        logi(TAG, "Entity To Db: entity = $entity")
        val result = NoteDb(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )
        logi(TAG, "Result = $result")
        return result
    }

    fun mapEntityListToDbList(list: List<NoteEntity>): List<NoteDb> {
        return list.map { mapEntityToDb(it) }
    }
}