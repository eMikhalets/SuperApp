package com.emikhalets.core.database.events.table_events

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "has_year") val hasYear: Boolean,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "note") val note: String,
)