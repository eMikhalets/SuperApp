package com.emikhalets.fitness.presentation.program

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.usecase.ProgramsUseCase
import com.emikhalets.fitness.presentation.program.ProgramContract.Action
import com.emikhalets.fitness.presentation.program.ProgramContract.Effect
import com.emikhalets.fitness.presentation.program.ProgramContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class ProgramViewModel @Inject constructor(
    private val programsUseCase: ProgramsUseCase,
) : BaseViewModel<Action, Effect, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        logd(TAG, "User event: $action")
        when (action) {
            is Action.GetProgram -> getProgram(action.id)
            is Action.DeleteProgram -> deleteProgram(action.entity)
        }
    }

    private fun getProgram(id: Long) {
        logd(TAG, "Get program: id = $id")
        if (id <= 0) return
        launchScope {
            programsUseCase.getItem(id)
                .onSuccess { flow -> setProgramState(flow) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun deleteProgram(entity: ProgramEntity?) {
        logd(TAG, "Delete program: $entity")
        if (entity == null) return
        launchScope {
            programsUseCase.delete(entity)
                .onSuccess { setEffect { Effect.ProgramDeleted } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private suspend fun setProgramState(flow: Flow<ProgramEntity>) {
        flow.collectLatest { entity ->
            logd(TAG, "Collecting program: $entity")
            setState { it.copy(isLoading = false, program = entity) }
        }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        logd(TAG, "Handling error: code = $code")
        setState { it.copy(isLoading = false) }
        setEffect { Effect.Error(message) }
    }

    companion object {

        private const val TAG = "ProgramVM"
    }
}
