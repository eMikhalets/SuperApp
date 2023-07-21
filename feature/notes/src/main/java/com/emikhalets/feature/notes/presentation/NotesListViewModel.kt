package com.emikhalets.feature.notes.presentation

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.feature.notes.domain.AddNoteUseCase
import com.emikhalets.feature.notes.domain.DeleteNoteUseCase
import com.emikhalets.feature.notes.domain.GetNotesUseCase
import com.emikhalets.feature.notes.domain.NoteModel
import com.emikhalets.feature.notes.presentation.NotesListContract.Action
import com.emikhalets.feature.notes.presentation.NotesListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : BaseViewModel<Action, State>() {

    init {
        logd(TAG, "Get Notes")
        launchScope {
            getNotesUseCase()
                .catch { handleFailure(it) }
                .collectLatest { setNotesList(it) }
        }
    }

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.DropDeleteDialog -> dropDeletingDialog()
            Action.DropNoteDialog -> dropNoteDialog()
            Action.DropError -> setState { it.copy(error = null) }
            Action.DeleteNote -> deleteNote()
            is Action.NoteClicked -> setEditTaskState(action.note)
            is Action.SetDeletingNote -> setDeletingNote(action.note)
            is Action.SaveNote -> saveNote(action.note)
        }
    }

    override fun handleError(message: String?) {
        setState { it.copy(error = UiString.create(message)) }
    }

    private fun dropDeletingDialog() {
        setState { it.copy(deletingNote = null) }
    }

    private fun dropNoteDialog() {
        setState { it.copy(showNoteDialog = false) }
    }

    private fun setEditTaskState(task: NoteModel) {
        setState { it.copy(editingNote = task, showNoteDialog = true) }
    }

    private fun setDeletingNote(task: NoteModel) {
        setState { it.copy(deletingNote = task) }
    }

    private fun saveNote(note: NoteModel) {
        setState { it.copy(showNoteDialog = false) }
        logd(TAG, "Save note: $note")
        launchScope {
            addNoteUseCase(note)
        }
    }

    private fun deleteNote() {
        dropDeletingDialog()
        val model = currentState.deletingNote ?: return
        logd(TAG, "Delete note: $model")
        launchScope {
            deleteNoteUseCase(model)
        }
    }

    private fun setNotesList(list: List<NoteModel>) {
        logd(TAG, "Collecting notes: list")
        setState { it.copy(isLoading = false, notesList = list) }
    }

    private fun handleFailure(throwable: Throwable) {
        logd(TAG, "Handling error: throwable = $throwable")
        setState { it.copy(isLoading = false, error = UiString.create(throwable)) }
    }

    companion object {

        private const val TAG = "NotesListVM"
    }
}
