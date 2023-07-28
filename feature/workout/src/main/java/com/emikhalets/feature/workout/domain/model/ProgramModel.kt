package com.emikhalets.feature.workout.domain.model

import com.emikhalets.core.database.workout.table_programs.ProgramDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ProgramModel(
    val id: Long,
    val name: String,
    val type: ProgramType,
    val workouts: List<WorkoutModel>,
) {

    constructor(type: ProgramType) : this("", type, emptyList())

    constructor(name: String, type: ProgramType, workouts: List<WorkoutModel>)
            : this(0, name, type, workouts)

    constructor(id: Long, name: String, type: ProgramType) : this(id, name, type, emptyList())

    companion object {

        fun Flow<List<ProgramModel>>.toDb(): Flow<List<ProgramDb>> = map { it.toDb() }

        fun List<ProgramModel>.toDb(): List<ProgramDb> = map { it.toDb() }

        fun ProgramModel.toDb(): ProgramDb = ProgramDb(
            id = id,
            name = name,
            type = type.toString(),
        )

        fun Flow<List<ProgramDb>>.toModel(): Flow<List<ProgramModel>> = map { it.toModel() }

        fun List<ProgramDb>.toModel(): List<ProgramModel> = map { it.toModel() }

        fun ProgramDb.toModel(): ProgramModel = ProgramModel(
            id = id,
            name = name,
            type = ProgramType.valueOf(type),
            workouts = emptyList()
        )
    }
}
