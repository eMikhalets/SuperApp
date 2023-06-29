package com.emikhalets.notes.data.mappers

import com.emikhalets.notes.data.database.table_notes.NoteDb
import com.emikhalets.notes.domain.entity.NoteEntity

object NotesMapper {

    fun mapDbToEntity(entity: NoteDb): NoteEntity =
        NoteEntity(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            createTimestamp = entity.createTimestamp,
            updateTimestamp = entity.updateTimestamp,
        )

    fun mapDbListToEntityList(list: List<NoteDb>): List<NoteEntity> =
        list.map { mapDbToEntity(it) }

    fun mapEntityToDb(entity: NoteEntity): NoteDb = NoteDb(
        id = entity.id,
        title = entity.title,
        content = entity.content,
        createTimestamp = entity.createTimestamp,
        updateTimestamp = entity.updateTimestamp,
    )

    fun mapEntityListToDbList(list: List<NoteEntity>): List<NoteDb> =
        list.map { mapEntityToDb(it) }
}