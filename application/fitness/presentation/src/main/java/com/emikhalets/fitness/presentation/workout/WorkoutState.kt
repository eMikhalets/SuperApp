package com.emikhalets.fitness.presentation.workout

import com.emikhalets.common.UiString
import com.emikhalets.fitness.domain.FitnessConst
import com.emikhalets.fitness.domain.entity.WorkoutEntity

data class WorkoutState(
    val workout: WorkoutEntity? = null,
    val currentRep: Int = 0,
    val timerValue: Int = FitnessConst.WORKOUT_TIMER,
    val isTimerActive: Boolean = false,
    val isCompleted: Boolean = false,
    val error: UiString? = null,
)
