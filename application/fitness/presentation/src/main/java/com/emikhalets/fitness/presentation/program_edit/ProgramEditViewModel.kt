package com.emikhalets.fitness.presentation.program_edit

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.usecase.GetProgramUseCase
import com.emikhalets.fitness.domain.usecase.SaveProgramUseCase
import com.emikhalets.fitness.presentation.program_edit.ProgramEditContract.Action
import com.emikhalets.fitness.presentation.program_edit.ProgramEditContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProgramEditViewModel @Inject constructor(
    private val getProgramUseCase: GetProgramUseCase,
    private val saveProgramUseCase: SaveProgramUseCase,
) : BaseViewModel<Action, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        logd(TAG, "User event: $action")
        when (action) {
            Action.DropError -> dropErrorState()
            is Action.GetProgram -> getProgram(action.id)
            is Action.SaveProgram -> saveProgram(action.name, action.workouts)
        }
    }

    private fun dropErrorState() {
        setState { it.copy(error = null) }
    }

    private fun getProgram(id: Long) {
        logd(TAG, "Get program: id = $id")
        if (id <= 0) return
        launchScope {
            setState { it.copy(isLoading = true) }
            getProgramUseCase(id)
                .onSuccess { entity -> setState { it.copy(isLoading = false, program = entity) } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun saveProgram(name: String, workouts: List<WorkoutEntity>) {
        logd(TAG, "Save program: name = $name, workouts = $workouts")
        launchScope {
            setState { it.copy(isLoading = true) }
            saveProgramUseCase(name, workouts)
                .onSuccess { setState { it.copy(isLoading = false, isProgramSaved = true) } }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        logd(TAG, "Handling error: code = $code")
        setState { it.copy(isLoading = false, error = message) }
    }

    companion object {

        private const val TAG = "ProgramEditVM"
    }
}
