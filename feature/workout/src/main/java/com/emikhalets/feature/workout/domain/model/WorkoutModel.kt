package com.emikhalets.feature.workout.domain.model

import java.util.Date

data class WorkoutModel(
    val id: Long,
    val name: String,
    val date: Long,
    val exercises: List<ExerciseModel>,
) {

    constructor() : this(0, "", Date().time, emptyList())
}
