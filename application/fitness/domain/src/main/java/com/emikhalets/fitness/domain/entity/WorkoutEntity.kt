package com.emikhalets.fitness.domain.entity

data class WorkoutEntity(
    val id: Long,
    val name: String,
    val exercises: List<ExerciseEntity>,
) {

    constructor() : this(0, "", emptyList())
}
