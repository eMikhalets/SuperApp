package com.emikhalets.notes.presentation.screens.notes

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiAction
import com.emikhalets.core.common.UiEffect
import com.emikhalets.core.common.UiState
import com.emikhalets.core.ui.UiString
import com.emikhalets.notes.domain.entity.NoteEntity

object NotesListContract {

    @Immutable
    sealed class Action : UiAction {

        object AddNoteDialog : Action()
        class EditNoteDialog(val note: NoteEntity) : Action()
        class DeleteNoteDialog(val note: NoteEntity) : Action()
        class AddNote(val note: NoteEntity?) : Action()
        class EditNote(val note: NoteEntity?) : Action()
        class DeleteNote(val note: NoteEntity?) : Action()
        object GetNotes : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {

        object AddNoteDialog : Effect()
        class EditNoteDialog(val entity: NoteEntity) : Effect()
        class DeleteNoteDialog(val entity: NoteEntity) : Effect()
        class Error(val message: UiString?) : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val notesList: List<NoteEntity> = emptyList(),
    ) : UiState
}
