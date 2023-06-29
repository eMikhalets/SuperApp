package com.emikhalets.notes.presentation.screens.note_item

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiEffect
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.core.common.UiString
import com.emikhalets.notes.domain.entity.NoteEntity

object NoteItemContract {

    @Immutable
    sealed class Action : UiAction {

        object DeleteNoteDialog : Action()
        object DeleteNote : Action()
        class SaveNote(val entity: NoteEntity?) : Action()
        class GetNote(val id: Long) : Action()
    }

    @Immutable
    sealed class Effect : UiEffect {

        object NoteSaved : Effect()
        object NoteDeleted : Effect()
        object DeleteNoteDialog : Effect()
        class Error(val message: UiString?) : Effect()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val noteEntity: NoteEntity? = null,
    ) : UiState
}
