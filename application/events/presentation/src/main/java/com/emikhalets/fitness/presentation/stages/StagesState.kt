package com.emikhalets.fitness.presentation.stages

import com.emikhalets.common.UiString

data class StagesState(
    val workoutList: List<WorkoutEntity> = emptyList(),
    val isNeedWorkout: Boolean = false,
    val error: UiString? = null,
)
