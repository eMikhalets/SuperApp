package com.emikhalets.notes.data.mappers

import com.emikhalets.notes.data.database.table_notes.NoteDb
import com.emikhalets.notes.domain.entity.NoteEntity

object NotesMapper {

    fun mapDbToEntity(dbEntity: NoteDb): NoteEntity =
        NoteEntity(
            id = dbEntity.id,
            title = dbEntity.title,
            content = dbEntity.content,
            createTimestamp = dbEntity.createTimestamp,
            updateTimestamp = dbEntity.updateTimestamp,
        )

    fun mapDbListToEntityList(dbList: List<NoteDb>): List<NoteEntity> =
        dbList.map { mapDbToEntity(it) }

    fun mapEntityToDb(entity: NoteEntity): NoteDb = NoteDb(
        id = entity.id,
        title = entity.title,
        content = entity.content,
        createTimestamp = entity.createTimestamp,
        updateTimestamp = entity.updateTimestamp,
    )

    fun mapEntityListToDbList(entityList: List<NoteEntity>): List<NoteDb> =
        entityList.map { mapEntityToDb(it) }
}