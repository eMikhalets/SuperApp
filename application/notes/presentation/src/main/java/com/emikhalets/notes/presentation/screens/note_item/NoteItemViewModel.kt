package com.emikhalets.notes.presentation.screens.note_item

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.usecase.NotesUseCase
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.Action
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NoteItemViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
) : BaseViewModel<Action, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        logd(TAG, "User event: $action")
        when (action) {
            Action.DropError -> dropErrorState()
            Action.DeleteNote -> deleteNote()
            is Action.SaveNote -> updateNote(action.entity)
            is Action.GetNote -> getNote(action.id)
        }
    }

    private fun dropErrorState() {
        setState { it.copy(error = null) }
    }

    private fun getNote(id: Long) {
        logd(TAG, "Get note: id = $id")
        if (id <= 0) return
        launchScope {
            notesUseCase.getItem(id)
                .onSuccess { item -> setNoteState(item) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun updateNote(entity: NoteEntity?) {
        logd(TAG, "Update note: entity = $entity")
        entity ?: return
        launchScope {
            if (entity.id == 0L) {
                notesUseCase.insert(entity)
                    .onFailure { code, message -> handleFailure(code, message) }
            } else {
                notesUseCase.update(entity.copy(updateTimestamp = Date().time))
                    .onSuccess { setState { it.copy(isNoteSaved = true) } }
                    .onFailure { code, message -> handleFailure(code, message) }
            }
        }
    }

    private fun deleteNote() {
        val entity = currentState.noteEntity
        logd(TAG, "Delete note: entity = $entity")
        entity ?: return
        launchScope {
            notesUseCase.delete(entity)
                .onSuccess { setState { it.copy(isNoteDeleted = true) } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun setNoteState(item: NoteEntity) {
        logd(TAG, "Set note state: entity = $item")
        setState { it.copy(isLoading = false, noteEntity = item) }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        logd(TAG, "Handle error: code = $code")
        setState { it.copy(isLoading = false, error = message) }
    }

    companion object {

        private const val TAG = "NoteItemVM"
    }
}
