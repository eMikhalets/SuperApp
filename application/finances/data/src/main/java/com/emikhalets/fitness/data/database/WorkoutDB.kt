package com.emikhalets.fitness.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "stage") val stage: Int,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "done_type") val doneType: String,
)
