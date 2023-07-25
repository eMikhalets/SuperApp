package com.emikhalets.feature.workout.domain.model

data class ProgramModel(
    val id: Long,
    val name: String,
    val type: ProgramType,
    val workouts: List<WorkoutModel>,
) {

    constructor(type: ProgramType) : this("", type, emptyList())

    constructor(name: String, type: ProgramType, workouts: List<WorkoutModel>)
            : this(0, name, type, workouts)
}
