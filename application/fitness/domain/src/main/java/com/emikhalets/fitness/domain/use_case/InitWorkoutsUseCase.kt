package com.emikhalets.fitness.domain.use_case

import com.emikhalets.fitness.domain.entity.WorkoutData
import com.emikhalets.fitness.domain.entity.WorkoutDoneType
import com.emikhalets.fitness.domain.entity.WorkoutEntity
import com.emikhalets.fitness.domain.entity.WorkoutType
import com.emikhalets.fitness.domain.repository.FitnessRepository
import java.util.Date
import javax.inject.Inject

class InitWorkoutsUseCase @Inject constructor(
    private val repository: FitnessRepository,
) {

    suspend operator fun invoke(): Result<List<Long>> {
        val date = Date().time
        val list = buildList {
            repeat(WorkoutData.pressStages.size) { num ->
                add(getEmptyWorkout(num + 1, date, WorkoutType.PRESS))
            }
            repeat(WorkoutData.pullUpStages.size) { num ->
                add(getEmptyWorkout(num + 1, date, WorkoutType.PULL_UP))
            }
            repeat(WorkoutData.pushUpStages.size) { num ->
                add(getEmptyWorkout(num + 1, date, WorkoutType.PUSH_UP))
            }
            repeat(WorkoutData.squatStages.size) { num ->
                add(getEmptyWorkout(num + 1, date, WorkoutType.SQUAT))
            }
        }
        return repository.insertWorkouts(list)
    }

    private fun getEmptyWorkout(stage: Int, date: Long, type: WorkoutType): WorkoutEntity {
        return WorkoutEntity(
            id = 0,
            stage = stage,
            date = date,
            type = type,
            doneType = WorkoutDoneType.NOT_DONE,
            reps = emptyList(),
        )
    }
}
