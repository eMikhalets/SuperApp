package com.emikhalets.notes.data.database.table_notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "create_timestamp") val createTimestamp: Long,
    @ColumnInfo(name = "update_timestamp") val updateTimestamp: Long,
)
