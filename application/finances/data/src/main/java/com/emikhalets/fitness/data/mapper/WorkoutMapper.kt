package com.emikhalets.fitness.data.mapper

import com.emikhalets.fitness.data.database.WorkoutDB
import com.emikhalets.fitness.domain.entity.WorkoutData
import com.emikhalets.fitness.domain.entity.WorkoutDoneType
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType

object WorkoutMapper {

    private fun mapDbToEntity(entity: WorkoutDB): WorkoutEntity {
        val type = WorkoutType.valueOf(entity.type)
        val stages = getStages(type, entity.stage)
        return WorkoutEntity(
            id = entity.id,
            stage = entity.stage,
            date = entity.date,
            type = WorkoutType.valueOf(entity.type),
            doneType = WorkoutDoneType.valueOf(entity.doneType),
            reps = stages
        )
    }

    fun mapEntityToDb(entity: WorkoutEntity): WorkoutDB = WorkoutDB(
        id = entity.id,
        stage = entity.stage,
        date = entity.date,
        type = entity.type.toString(),
        doneType = entity.doneType.toString()
    )

    fun mapDbListToList(list: List<WorkoutDB>): List<WorkoutEntity> = list.map {
        mapDbToEntity(it)
    }

    fun mapListToDbList(list: List<WorkoutEntity>): List<WorkoutDB> = list.map {
        mapEntityToDb(it)
    }

    private fun getStages(type: WorkoutType, stage: Int): List<Int> {
        return when (type) {
            WorkoutType.SQUAT -> convertStages(WorkoutData.squatStages[stage - 1])
            WorkoutType.PUSH_UP -> convertStages(WorkoutData.pushUpStages[stage - 1])
            WorkoutType.PULL_UP -> convertStages(WorkoutData.pullUpStages[stage - 1])
            WorkoutType.PRESS -> convertStages(WorkoutData.pressStages[stage - 1])
        }
    }

    private fun convertStages(stages: String): List<Int> {
        return try {
            stages.split(" ").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
