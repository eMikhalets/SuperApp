package com.emikhalets.feature.notes.data

import com.emikhalets.core.database.notes.table_notes.NoteDb

fun List<NoteModel>.toDbList(): List<NoteDb> = map { it.toDb() }

fun List<NoteDb>.toModelList(): List<NoteModel> = map { it.toModel() }

fun NoteModel.toDb(): NoteDb = NoteDb(
    id = id,
    title = title,
    content = content,
    createDate = createDate,
    updateDate = updateDate,
)

fun NoteDb.toModel(): NoteModel = NoteModel(
    id = id,
    title = title,
    content = content,
    createDate = createDate,
    updateDate = updateDate,
)