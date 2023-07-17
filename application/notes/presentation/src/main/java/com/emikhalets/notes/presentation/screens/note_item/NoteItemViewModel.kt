package com.emikhalets.notes.presentation.screens.note_item

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.usecase.notes.AddNoteUseCase
import com.emikhalets.notes.domain.usecase.notes.DeleteNoteUseCase
import com.emikhalets.notes.domain.usecase.notes.GetNoteUseCase
import com.emikhalets.notes.domain.usecase.notes.UpdateNoteUseCase
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.Action
import com.emikhalets.notes.presentation.screens.note_item.NoteItemContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NoteItemViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
) : BaseViewModel<Action, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        logd(TAG, "User event: ${action.javaClass.simpleName}")
        when (action) {
            Action.DropError -> setState { it.copy(error = null) }
            Action.DropDeleting -> setState { it.copy(deletedEntity = null) }
            Action.SetDeletedEntity -> setDeletedEntityState()
            Action.SaveNote -> updateNote()
            is Action.DeleteNote -> deleteNote()
            is Action.GetNote -> getNote(action.id)
            is Action.SetContent -> setContentState(action.value)
            is Action.SetTitle -> setTitleState(action.value)
        }
    }

    private fun setTitleState(value: String) {
        setState { it.copy(title = value, isNeedSave = true) }
    }

    private fun setContentState(value: String) {
        setState { it.copy(title = value, isNeedSave = true) }
    }

    private fun setDeletedEntityState() {
        setState { it.copy(deletedEntity = currentState.noteEntity) }
    }

    private fun getNote(id: Long) {
        logd(TAG, "Get note: id = $id")
        if (id <= 0) return
        launchScope {
            setState { it.copy(isLoading = true) }
            getNoteUseCase(id)
                .onSuccess { item -> setNoteState(item) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun updateNote() {
        val entity = currentState.noteEntity
        logd(TAG, "Update note: entity = $entity")
        launchScope {
            if (entity == null) {
                addNoteUseCase(NoteEntity(currentState.title, currentState.content))
                    .onFailure { code, message -> handleFailure(code, message) }
            } else {
                val newEntity = entity.copy(
                    title = currentState.title,
                    content = currentState.content,
                    updateTimestamp = Date().time
                )
                updateNoteUseCase(newEntity)
                    .onSuccess { setState { it.copy(isNoteSaved = true) } }
                    .onFailure { code, message -> handleFailure(code, message) }
            }
        }
    }

    private fun deleteNote() {
        val entity = currentState.noteEntity
        setState { it.copy(deletedEntity = null) }
        logd(TAG, "Delete note: entity = $entity")
        entity ?: return
        launchScope {
            deleteNoteUseCase(entity)
                .onSuccess { setState { it.copy(isNoteDeleted = true) } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun setNoteState(item: NoteEntity) {
        logd(TAG, "Set note state: entity = $item")
        setState {
            it.copy(
                isLoading = false,
                noteEntity = item,
                title = item.title,
                content = item.content,
                date = item.updateTimestamp
            )
        }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        logd(TAG, "Handle error: code = $code")
        setState { it.copy(isLoading = false, error = message) }
    }

    companion object {

        private const val TAG = "NoteItemVM"
    }
}
