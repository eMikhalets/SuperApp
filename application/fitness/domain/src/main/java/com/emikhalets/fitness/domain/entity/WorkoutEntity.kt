package com.emikhalets.fitness.domain.entity

data class WorkoutEntity(
    val id: Long,
    val exercises: List<ExerciseEntity>,
)
