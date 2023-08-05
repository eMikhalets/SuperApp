//package com.emikhalets.feature.workout.presentation.programs
//
//import com.emikhalets.core.common.UiString
//import com.emikhalets.core.common.mvi.MviViewModel
//import com.emikhalets.core.common.mvi.launchScope
//import com.emikhalets.feature.workout.domain.GetProgramsUseCase
//import com.emikhalets.feature.workout.domain.model.ProgramModel
//import com.emikhalets.feature.workout.presentation.programs.ProgramsContract.Action
//import com.emikhalets.feature.workout.presentation.programs.ProgramsContract.Effect
//import com.emikhalets.feature.workout.presentation.programs.ProgramsContract.State
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.collectLatest
//
//@HiltViewModel
//class ProgramsViewModel @Inject constructor(
//    private val getProgramsUseCase: GetProgramsUseCase,
//) : MviViewModel<Action, Effect, State>() {
//
//    init {
//        launchScope {
//            getProgramsUseCase()
//                .catch { handleFailure(it) }
//                .collectLatest { setProgramsList(it) }
//        }
//    }
//
//    override fun createInitialState() = State()
//
//    override fun handleEvent(action: Action) {
//        when (action) {
//            Action.DropError -> dropErrorState()
//            Action.AddProgramClick -> setEffect { Effect.NavigateToNewProgram }
//            is Action.ProgramClick -> setEffect { Effect.NavigateToProgram(action.id) }
//        }
//    }
//
//    override fun handleError(message: String?) {
//        setState { it.copy(error = UiString.create(message)) }
//    }
//
//    private fun dropErrorState() {
//        setState { it.copy(error = null) }
//    }
//
//    private fun addNewProgram(list: List<ProgramModel>) {
//        setState { it.copy(isLoading = false, programs = list) }
//    }
//
//    private fun setProgramsList(list: List<ProgramModel>) {
//        setState { it.copy(isLoading = false, programs = list) }
//    }
//
//    private fun handleFailure(throwable: Throwable) {
//        setState { it.copy(isLoading = false, error = UiString.create(throwable)) }
//    }
//}
