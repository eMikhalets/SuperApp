package com.emikhalets.fitness.domain.entity

import com.emikhalets.fitness.domain.entity.enums.ProgramType

data class ProgramEntity(
    val id: Long,
    val name: String,
    val workouts: List<WorkoutEntity>,
    val type: ProgramType,
) {

    constructor(name: String, workouts: List<WorkoutEntity>, type: ProgramType) : this(
        0,
        name,
        workouts,
        type
    )
}
