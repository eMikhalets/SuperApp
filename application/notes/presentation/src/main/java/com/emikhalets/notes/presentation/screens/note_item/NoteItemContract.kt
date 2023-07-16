package com.emikhalets.notes.presentation.screens.note_item

import androidx.compose.runtime.Immutable
import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.mvi.UiAction
import com.emikhalets.core.common.mvi.UiState
import com.emikhalets.notes.domain.entity.NoteEntity

object NoteItemContract {

    @Immutable
    sealed class Action : UiAction {

        object DropError : Action()
        object DeleteNote : Action()
        class SaveNote(val entity: NoteEntity?) : Action()
        class GetNote(val id: Long) : Action()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val isNoteDeleted: Boolean = false,
        val isNoteSaved: Boolean = false,
        val noteEntity: NoteEntity? = null,
        val error: UiString? = null,
    ) : UiState
}
