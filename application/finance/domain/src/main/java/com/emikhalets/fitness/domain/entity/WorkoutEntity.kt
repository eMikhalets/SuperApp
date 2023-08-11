package com.emikhalets.fitness.domain.entity

data class WorkoutEntity(
    val id: Long,
    val stage: Int,
    val date: Long,
    val type: WorkoutType,
    val doneType: WorkoutDoneType,
    val reps: List<Int>,
) {

    val repsCount: Int = reps.sumOf { it }
}
