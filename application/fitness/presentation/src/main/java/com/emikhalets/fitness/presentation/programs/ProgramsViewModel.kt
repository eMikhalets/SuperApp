package com.emikhalets.fitness.presentation.programs

import com.emikhalets.core.common.UiString
import com.emikhalets.core.common.logd
import com.emikhalets.core.common.mvi.BaseViewModel
import com.emikhalets.core.common.mvi.launchScope
import com.emikhalets.core.common.onFailure
import com.emikhalets.core.common.onSuccess
import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.usecase.GetProgramsFlowUseCase
import com.emikhalets.fitness.presentation.programs.ProgramsContract.Action
import com.emikhalets.fitness.presentation.programs.ProgramsContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class ProgramsViewModel @Inject constructor(
    private val getProgramsFlowUseCase: GetProgramsFlowUseCase,
) : BaseViewModel<Action, State>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        logd(TAG, "User event: ${action.javaClass.simpleName}")
        when (action) {
            Action.DropError -> dropErrorState()
            Action.GetPrograms -> getPrograms()
        }
    }

    private fun dropErrorState() {
        setState { it.copy(error = null) }
    }

    private fun getPrograms() {
        logd(TAG, "Get programs")
        launchScope {
            setState { it.copy(isLoading = true) }
            getProgramsFlowUseCase()
                .onSuccess { flow -> setProgramsState(flow) }
                .onFailure { code, message -> handleFailure(code, message) }
        }
    }

    private suspend fun setProgramsState(flow: Flow<List<ProgramEntity>>) {
        flow.collectLatest { list ->
            logd(TAG, "Collecting programs: $list")
            setState { it.copy(isLoading = false, programs = list) }
        }
    }

    private fun handleFailure(code: Int, message: UiString?) {
        logd(TAG, "Handling error: code = $code")
        setState { it.copy(isLoading = false, error = message) }
    }

    companion object {

        private const val TAG = "ProgramsVM"
    }
}
