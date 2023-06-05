package com.emikhalets.simplenotes.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subtasks_table")
data class SubtaskDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "task_id") val taskId: Long,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "checked") val checked: Boolean,
    @ColumnInfo(name = "saved_time") val savedTime: Long,
)
