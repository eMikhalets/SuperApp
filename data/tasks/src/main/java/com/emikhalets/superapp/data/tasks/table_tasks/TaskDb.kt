package com.emikhalets.superapp.data.tasks.table_tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "parent_id")
    val parentId: Long,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "completed")
    val completed: Boolean,
    @ColumnInfo(name = "create_date")
    val createDate: Long,
    @ColumnInfo(name = "update_date")
    val updateDate: Long,
)
