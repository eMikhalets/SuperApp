package com.emikhalets.notes.presentation.screens.notes

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.usecase.NotesUseCase
import com.emikhalets.notes.presentation.screens.notes.NotesListContract.Action
import com.emikhalets.notes.presentation.screens.notes.NotesListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
) : BaseViewModel<Action, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        logd(TAG, "User event: $action")
        when (action) {
            Action.DropError -> dropErrorState()
            Action.GetNotes -> getNotes()
            is Action.DeleteNote -> deleteNote(action.note)
        }
    }

    private fun dropErrorState() {
        setState { it.copy(error = null) }
    }

    private fun getNotes() {
        logd(TAG, "Get notes")
        launchScope {
            notesUseCase.getAllFlow()
                .onSuccess { flow -> setAllNotesState(flow) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun deleteNote(entity: NoteEntity?) {
        logd(TAG, "Delete note: entity = $entity")
        entity ?: return
        launchScope {
            notesUseCase.delete(entity)
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private suspend fun setAllNotesState(flow: Flow<List<NoteEntity>>) {
        flow.collectLatest { list ->
            logd(TAG, "Collecting notes: list = $list")
            setState { it.copy(isLoading = false, notesList = list) }
        }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        logd(TAG, "Handle error: code = $code")
        setState { it.copy(isLoading = false, error = message) }
    }

    companion object {

        private const val TAG = "NotesListVM"
    }
}
