//package com.emikhalets.feature.workout.presentation.program_add
//
//import androidx.compose.runtime.Immutable
//import com.emikhalets.core.common.UiString
//import com.emikhalets.core.common.mvi.UiAction
//import com.emikhalets.core.common.mvi.UiEffect
//import com.emikhalets.core.common.mvi.UiState
//import com.emikhalets.fitness.domain.entity.ProgramEntity
//import com.emikhalets.fitness.domain.entity.WorkoutEntity
//
//object ProgramAddContract {
//
//    @Immutable
//    sealed class Action : UiAction {
//
//        object DropError : Action()
//        class SaveProgram(val name: String, val workouts: List<WorkoutEntity>) : Action()
//        class GetProgram(val id: Long) : Action()
//    }
//
//    @Immutable
//    sealed class Effect : UiEffect {
//
//        object NavigateToNewProgram : Effect()
//        class NavigateToProgram(val id: Long) : Effect()
//    }
//
//    @Immutable
//    data class State(
//        val isLoading: Boolean = false,
//        val isProgramSaved: Boolean = false,
//        val program: ProgramEntity? = null,
//        val error: UiString? = null,
//    ) : UiState
//}
