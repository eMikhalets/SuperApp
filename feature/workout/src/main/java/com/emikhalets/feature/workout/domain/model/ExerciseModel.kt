package com.emikhalets.feature.workout.domain.model

data class ExerciseModel(
    val id: Long,
    val name: String,
    val reps: List<Int>,
)
