package com.emikhalets.feature.workout.domain.model

data class WorkoutModel(
    val id: Long,
    val name: String,
    val date: Long,
    val exercises: List<ExerciseModel>,
)
