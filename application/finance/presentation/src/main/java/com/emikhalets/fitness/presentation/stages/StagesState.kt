package com.emikhalets.fitness.presentation.stages

import com.emikhalets.common.UiString
import com.emikhalets.fitness.domain.entity.WorkoutEntity

data class StagesState(
    val workoutList: List<WorkoutEntity> = emptyList(),
    val isNeedWorkout: Boolean = false,
    val error: UiString? = null,
)
