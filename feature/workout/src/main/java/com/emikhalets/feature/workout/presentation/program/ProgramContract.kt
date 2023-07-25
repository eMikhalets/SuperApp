//package com.emikhalets.fitness.presentation.program
//
//import androidx.compose.runtime.Immutable
//import com.emikhalets.core.common.UiString
//import com.emikhalets.core.common.mvi.UiAction
//import com.emikhalets.core.common.mvi.UiEffect
//import com.emikhalets.core.common.mvi.UiState
//import com.emikhalets.fitness.domain.entity.ProgramEntity
//
//object ProgramContract {
//
//    @Immutable
//    sealed class Action : UiAction {
//
//        object DropError : Action()
//        class DeleteProgram(val entity: ProgramEntity?) : Action()
//        class GetProgram(val id: Long) : Action()
//    }
//
//    @Immutable
//    sealed class Effect : UiEffect {
//    }
//
//    @Immutable
//    data class State(
//        val isLoading: Boolean = false,
//        val isProgramDeleted: Boolean = false,
//        val program: ProgramEntity? = null,
//        val error: UiString? = null,
//    ) : UiState
//}
