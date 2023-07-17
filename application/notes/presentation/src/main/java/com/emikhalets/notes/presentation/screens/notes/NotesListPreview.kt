package com.emikhalets.notes.presentation.screens.notes

import com.emikhalets.notes.domain.entity.NoteEntity

fun getNotesListPreview(): List<NoteEntity> {
    return listOf(
        NoteEntity(
            "Note title",
            "Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content"
        ),
        NoteEntity("Note title 1", "Note content Note content Note content"),
        NoteEntity("Note title 2", "Note content"),
        NoteEntity("Note title 3", "Note content Note content"),
        NoteEntity("Note title 4", "Note content"),
        NoteEntity("Note title 5", "Note content"),
        NoteEntity("Note title 6", "Note content Note content Note content Note content Note content"),
    )
}