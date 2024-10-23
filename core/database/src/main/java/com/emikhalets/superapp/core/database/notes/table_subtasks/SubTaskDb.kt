package com.emikhalets.superapp.core.database.notes.table_subtasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subtasks")
data class SubTaskDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "parent_id")
    val parentId: Long,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "completed")
    val completed: Boolean,
    @ColumnInfo(name = "create_date")
    val createDate: Long,
)
