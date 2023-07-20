package com.emikhalets.feature.notes.presentation

import com.emikhalets.feature.notes.data.NoteModel

fun getNotesListPreview(): List<NoteModel> {
    return listOf(
        NoteModel(
            1,
            "Note title",
            "Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content Note content"
        ),
        NoteModel(2, "Note title 1", "Note content Note content Note content"),
        NoteModel(3, "Note title 2", "Note content"),
        NoteModel(4, "Note title 3", "Note content Note content"),
        NoteModel(5, "Note title 4", "Note content"),
        NoteModel(6, "Note title 5", "Note content"),
        NoteModel(
            7,
            "Note title 6",
            "Note content Note content Note content Note content Note content"
        ),
    )
}