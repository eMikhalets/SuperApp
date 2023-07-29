package com.emikhalets.feature.workout.domain.model

import java.util.Date

data class WorkoutModel(
    val id: Long,
    val name: String,
    val date: Long,
    val exercises: List<ExerciseModel>,
) {

    constructor() : this(0, "")

    constructor(id: Long, name: String) : this(id, name, Date().time, emptyList())

    constructor(id: Long, name: String, exercises: List<ExerciseModel>)
            : this(id, name, Date().time, exercises)
}
