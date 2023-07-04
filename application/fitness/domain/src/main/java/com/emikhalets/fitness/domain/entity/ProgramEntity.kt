package com.emikhalets.fitness.domain.entity

data class ProgramEntity(
    val id: Long,
    val name: String,
    val workouts: List<WorkoutEntity>,
) {

    constructor(name: String, workouts: List<WorkoutEntity>) : this(0, name, workouts)
}
