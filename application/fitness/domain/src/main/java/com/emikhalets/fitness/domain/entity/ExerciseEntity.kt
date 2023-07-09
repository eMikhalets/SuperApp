package com.emikhalets.fitness.domain.entity

data class ExerciseEntity(
    val id: Long,
    val name: String,
    val type: ExerciseType,
    val reps: MutableList<Int>,
)
