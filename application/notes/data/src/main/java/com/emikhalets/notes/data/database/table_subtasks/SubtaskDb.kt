package com.emikhalets.notes.data.database.table_subtasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subtasks")
data class SubtaskDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "task_id") val taskId: Long,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean,
    @ColumnInfo(name = "create_timestamp") val createTimestamp: Long,
    @ColumnInfo(name = "update_timestamp") val updateTimestamp: Long,
)
