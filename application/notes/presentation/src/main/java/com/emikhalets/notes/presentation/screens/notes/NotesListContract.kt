package com.emikhalets.notes.presentation.screens.notes

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.core.common.UiString
import com.emikhalets.notes.domain.entity.NoteEntity

object NotesListContract {

    @Immutable
    sealed class Action : UiAction {

        class DeleteNoteDialog(val note: NoteEntity) : Action()
        class DeleteNote(val note: NoteEntity?) : Action()
        object GetNotes : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {

        class DeleteNoteDialog(val entity: NoteEntity) : Effect()
        class Error(val message: com.emikhalets.core.common.UiString?) : Effect()
        class NavigateToNewNote(val id: Long?) : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val notesList: List<NoteEntity> = emptyList(),
    ) : UiState
}