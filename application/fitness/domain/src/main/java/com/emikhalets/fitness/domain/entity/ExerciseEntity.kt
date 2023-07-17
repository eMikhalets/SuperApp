package com.emikhalets.fitness.domain.entity

data class ExerciseEntity(
    val id: Long,
    val name: String,
    val reps: List<Int>,
) {

    constructor() : this(0, "", emptyList())

    constructor(id: Long, name: String) : this(id, name, emptyList())
}
