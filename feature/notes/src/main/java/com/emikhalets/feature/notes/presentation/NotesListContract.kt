package com.emikhalets.feature.notes.presentation

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.feature.notes.domain.NoteModel

object NotesListContract {

    @Immutable
    sealed class Action : UiAction {

        object DropDeleteDialog : Action()
        object DropNoteDialog : Action()
        object DropError : Action()
        object DeleteNote : Action()
        class NoteClicked(val note: NoteModel = NoteModel()) : Action()
        class SetDeletingNote(val note: NoteModel) : Action()
        class SaveNote(val note: NoteModel) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val notesList: List<NoteModel> = emptyList(),
        val editingNote: NoteModel? = null,
        val deletingNote: NoteModel? = null,
        val showNoteDialog: Boolean = false,
        val error: UiString? = null,
    ) : UiState
}
