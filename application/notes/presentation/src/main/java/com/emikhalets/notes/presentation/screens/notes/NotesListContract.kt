package com.emikhalets.notes.presentation.screens.notes

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.notes.domain.entity.NoteEntity

object NotesListContract {

    @Immutable
    sealed class Action : UiAction {

        object DropError : Action()
        class DeleteNote(val note: NoteEntity?) : Action()
        object GetNotes : Action()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val notesList: List<NoteEntity> = emptyList(),
        val error: UiString? = null,
    ) : UiState
}
