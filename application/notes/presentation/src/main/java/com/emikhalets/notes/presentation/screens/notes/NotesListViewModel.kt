package com.emikhalets.notes.presentation.screens.notes

import com.emikhalets.core.common.BaseViewModel
import com.emikhalets.core.common.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.core.ui.UiString
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.usecase.NotesUseCase
import com.emikhalets.notes.presentation.screens.notes.NotesListContract.Action
import com.emikhalets.notes.presentation.screens.notes.NotesListContract.Effect
import com.emikhalets.notes.presentation.screens.notes.NotesListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
) : BaseViewModel<Action, Effect, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.GetNotes -> getNotes()
            is Action.DeleteNote -> deleteNote(action.note)
            is Action.DeleteNoteDialog -> setEffect { Effect.DeleteNoteDialog(action.note) }
        }
    }

    private fun getNotes() {
        launchScope {
            notesUseCase.getAllFlow()
                .onSuccess { flow -> setAllNotesState(flow) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun deleteNote(entity: NoteEntity?) {
        entity ?: return
        launchScope {
            notesUseCase.delete(entity)
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private suspend fun setAllNotesState(flow: Flow<List<NoteEntity>>) {
        flow.collectLatest { list ->
            setState { it.copy(isLoading = false, notesList = list) }
        }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        setState { it.copy(isLoading = false) }
        setEffect { Effect.Error(message) }
    }
}
