package com.emikhalets.notes.presentation.screens.notes

import com.emikhalets.simplenotes.utils.UiString

data class NotesListState(
    val notesList: List<com.emikhalets.notes.domain.entity.NoteEntity> = emptyList(),
    val error: UiString? = null,
)
