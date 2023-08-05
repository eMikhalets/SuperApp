package com.emikhalets.core.database.workout.table_programs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("programs")
data class ProgramDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "size") val size: Int,
    @ColumnInfo(name = "type") val type: String,
)