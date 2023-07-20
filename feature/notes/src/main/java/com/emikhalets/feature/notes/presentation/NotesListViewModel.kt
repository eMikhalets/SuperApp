package com.emikhalets.feature.notes.presentation

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.feature.notes.data.NoteModel
import com.emikhalets.feature.notes.data.Repository
import com.emikhalets.feature.notes.presentation.NotesListContract.Action
import com.emikhalets.feature.notes.presentation.NotesListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel<Action, State>() {

    init {
        logd(TAG, "Get Notes")
        launchScope {
            repository.getNotes()
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
            if (note.id == 0L) repository.insertNote(note)
            else repository.updateNote(note)
        }
    }

    private fun deleteNote() {
        dropDeletingDialog()
        val model = currentState.deletingNote ?: return
        logd(TAG, "Delete note: $model")
        launchScope {
            repository.deleteNote(model)
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
