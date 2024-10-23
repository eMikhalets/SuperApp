package com.emikhalets.superapp.core.database.notes.table_tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "header")
    val header: String,
    @ColumnInfo(name = "create_date")
    val createDate: Long,
)
