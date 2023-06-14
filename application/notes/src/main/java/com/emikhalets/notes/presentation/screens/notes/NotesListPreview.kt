package com.emikhalets.notes.presentation.screens.notes

import com.emikhalets.notes.domain.entity.NoteEntity

fun getNotesListPreview(): List<NoteEntity> {
    return listOf(
        NoteEntity("Note title", "Note content"),
        NoteEntity("Note title", "Note content"),
        NoteEntity("Note title", "Note content"),
        NoteEntity("Note title", "Note content"),
        NoteEntity("Note title", "Note content"),
        NoteEntity("Note title", "Note content"),
        NoteEntity("Note title", "Note content"),
    )
}