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
        object DropDeleting : Action()
        object SetDeletedEntity : Action()
        object SaveNote : Action()
        class GetNote(val id: Long) : Action()
        class DeleteNote(val entity: NoteEntity?) : Action()
        class SetTitle(val value: String) : Action()
        class SetContent(val value: String) : Action()
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val isNoteDeleted: Boolean = false,
        val isNoteSaved: Boolean = false,
        val isNeedSave: Boolean = false,
        val noteEntity: NoteEntity? = null,
        val title: String = "",
        val content: String = "",
        val date: Long = 0,
        val deletedEntity: NoteEntity? = null,
        val error: UiString? = null,
    ) : UiState
}
