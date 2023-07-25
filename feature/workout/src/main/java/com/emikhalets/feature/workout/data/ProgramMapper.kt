package com.emikhalets.feature.workout.data

import com.emikhalets.core.database.workout.table_programs.ProgramDb
import com.emikhalets.feature.tasks.domain.model.ProgramModel
import com.emikhalets.feature.tasks.domain.model.ProgramType

fun List<ProgramModel>.toDbList(): List<ProgramDb> = map { it.toDb() }

fun List<ProgramDb>.toModelList(): List<ProgramModel> = map { it.toModel() }

fun ProgramModel.toDb(): ProgramDb = ProgramDb(
    id = id,
    name = name,
    type = type.toString(),
)

fun ProgramDb.toModel(): ProgramModel = ProgramModel(
    id = id,
    name = name,
    type = ProgramType.valueOf(type),
    workouts = emptyList()
)