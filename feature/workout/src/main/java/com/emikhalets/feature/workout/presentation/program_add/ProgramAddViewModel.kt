//package com.emikhalets.feature.workout.presentation.program_add
//
//import com.emikhalets.core.common.UiString
//import com.emikhalets.core.common.UndefinedIndex
//import com.emikhalets.core.common.loge
//import com.emikhalets.core.common.mvi.MviViewModel
//import com.emikhalets.core.common.mvi.launchScope
//import com.emikhalets.feature.workout.domain.AddProgramUseCase
//import com.emikhalets.feature.workout.domain.model.ExerciseModel
//import com.emikhalets.feature.workout.domain.model.ProgramModel
//import com.emikhalets.feature.workout.domain.model.ProgramType
//import com.emikhalets.feature.workout.domain.model.WorkoutModel
//import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.Action
//import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.Effect
//import com.emikhalets.feature.workout.presentation.program_add.ProgramAddContract.State
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class ProgramAddViewModel @Inject constructor(
//    private val addProgramUseCase: AddProgramUseCase,
//) : MviViewModel<Action, Effect, State>() {
//
//    override fun createInitialState() = State()
//
//    override fun handleEvent(action: Action) {
//        when (action) {
//            Action.DropError -> dropErrorState()
//            Action.AddProgram -> addProgram()
//            is Action.NameChanged -> setName(action.name)
//            is Action.TypeChanged -> setType(action.type)
//            is Action.ChangeWorkouts -> changeWorkoutsSize(action.index)
//            is Action.ChangeExercises -> changeExercisesSize(action.parentIndex, action.index)
//        }
//    }
//
//    override fun handleError(message: String?) {
//        setState { it.copy(error = UiString.create(message)) }
//    }
//
//    private fun changeWorkoutsSize(index: Int) {
//        if (currentState.workouts.any { it.name.isBlank() }) return
//        val newList = currentState.workouts.changeList(index, WorkoutModel())
//        setState { it.copy(workouts = newList) }
//    }
//
//    private fun changeExercisesSize(parentIndex: Int, index: Int) {
//        try {
//            val workouts = currentState.workouts.toMutableList()
//            val workout = workouts[parentIndex]
//            val newExercises = workout.exercises.changeList(index, ExerciseModel())
//            workouts[parentIndex] = workout.copy(exercises = newExercises)
//            setState { it.copy(workouts = workouts) }
//        } catch (e: Exception) {
//            loge(TAG, e)
//        }
//    }
//
//    private fun dropErrorState() {
//        setState { it.copy(error = null) }
//    }
//
//    private fun setName(name: String) {
//        setState { it.copy(name = name) }
//    }
//
//    private fun setType(type: ProgramType) {
//        setState { it.copy(type = type) }
//    }
//
//    private fun addProgram() {
//        launchScope {
//            val model = ProgramModel(currentState.name, currentState.type, currentState.workouts)
//            addProgramUseCase(model)
//            setEffect { Effect.ProgramAdded }
//        }
//    }
//
//    private fun <T> List<T>.changeList(index: Int, model: T): List<T> {
//        return this.toMutableList()
//            .also { if (index == UndefinedIndex) it.add(model) else it.removeAt(index) }
//    }
//
//    companion object {
//
//        private const val TAG = "ProgramAddVM"
//    }
//}
