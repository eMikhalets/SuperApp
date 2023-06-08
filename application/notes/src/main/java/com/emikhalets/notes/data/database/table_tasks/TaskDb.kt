package com.emikhalets.notes.data.database.table_tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean,
    @ColumnInfo(name = "create_timestamp") val createTimestamp: Long,
    @ColumnInfo(name = "update_timestamp") val updateTimestamp: Long,
)
