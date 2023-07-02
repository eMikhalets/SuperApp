package com.emikhalets.notes.data.mappers

import com.emikhalets.core.common.logi
import com.emikhalets.notes.data.database.table_notes.NoteDb
import com.emikhalets.notes.domain.entity.NoteEntity

object NotesMapper {

    private const val TAG = "NotesMapper"

    fun mapDbToEntity(entity: NoteDb): NoteEntity {
        logi(TAG, "mapDbToEntity(): entity = $entity")
        return NoteEntity(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )
    }

    fun mapDbListToEntityList(list: List<NoteDb>): List<NoteEntity> {
        logi(TAG, "mapDbListToEntityList(): size = ${list.count()}")
        return list.map { mapDbToEntity(it) }
    }

    fun mapEntityToDb(entity: NoteEntity): NoteDb {
        logi(TAG, "mapEntityToDb(): entity = $entity")
        return NoteDb(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )
    }

    fun mapEntityListToDbList(list: List<NoteEntity>): List<NoteDb> {
        logi(TAG, "mapEntityListToDbList(): size = ${list.count()}")
        return list.map { mapEntityToDb(it) }
    }
}