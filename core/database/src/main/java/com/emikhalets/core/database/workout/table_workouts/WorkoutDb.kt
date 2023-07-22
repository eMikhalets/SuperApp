package com.emikhalets.core.database.workout.table_workouts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("workout")
data class WorkoutDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "program_id") val programId: Long,
    @ColumnInfo(name = "name") val name: String,
)