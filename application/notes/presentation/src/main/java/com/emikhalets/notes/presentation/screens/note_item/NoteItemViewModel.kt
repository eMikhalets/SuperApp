package com.emikhalets.notes.presentation.screens.note_item

import com.emikhalets.core.common.BaseViewModel
import com.emikhalets.core.common.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.core.ui.UiString
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.usecase.NotesUseCase
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.Action
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.Effect
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NoteItemViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
) : BaseViewModel<Action, Effect, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.DeleteNote -> deleteNote()
            Action.DeleteNoteDialog -> setEffect { Effect.DeleteNoteDialog }
            is Action.SaveNote -> updateNote(action.entity)
            is Action.GetNote -> getNote(action.id)
        }
    }

    private fun getNote(id: Long) {
        if (id <= 0) return
        launchScope {
            notesUseCase.getItem(id)
                .onSuccess { item -> setNoteState(item) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun updateNote(entity: NoteEntity?) {
        entity ?: return
        launchScope {
            if (entity.id == 0L) {
                notesUseCase.insert(entity)
                    .onFailure { code, message -> handleFailure(code, message) }
            } else {
                notesUseCase.update(entity.copy(updateTimestamp = Date().time))
                    .onSuccess { setEffect { Effect.NoteSaved } }
                    .onFailure { code, message -> handleFailure(code, message) }
            }
        }
    }

    private fun deleteNote() {
        val entity = currentState.noteEntity
        entity ?: return
        launchScope {
            notesUseCase.delete(entity)
                .onSuccess { setEffect { Effect.NoteDeleted } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun setNoteState(item: NoteEntity) {
        setState { it.copy(isLoading = false, noteEntity = item) }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        setState { it.copy(isLoading = false) }
        setEffect { Effect.Error(message) }
    }
}
