package com.emikhalets.feature.workout.presentation.program_add

import com.emikhalets.feature.workout.domain.model.ExerciseModel
import com.emikhalets.feature.workout.domain.model.WorkoutModel

fun getProgramAddPreview(): List<WorkoutModel> {
    return listOf(
        WorkoutModel(1, "Workout name 1"),
        WorkoutModel(
            2, "Workout name 2",
            listOf(ExerciseModel("Exercise 1"), ExerciseModel("Exercise 2"))
        ),
        WorkoutModel(3, "Workout name 3"),
    )
}