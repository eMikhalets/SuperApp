package com.emikhalets.notes.presentation.screens.notes

import com.emikhalets.notes.domain.entity.NoteEntity

fun getNotesListPreview(): List<NoteEntity> {
    return listOf(
        NoteEntity(
            1,
            "Note title",
            "Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content"
        ),
        NoteEntity(2, "Note title 1", "Note content Note content Note content"),
        NoteEntity(3, "Note title 2", "Note content"),
        NoteEntity(4, "Note title 3", "Note content Note content"),
        NoteEntity(5, "Note title 4", "Note content"),
        NoteEntity(6, "Note title 5", "Note content"),
        NoteEntity(
            7,
            "Note title 6",
            "Note content Note content Note content Note content Note content"
        ),
    )
}