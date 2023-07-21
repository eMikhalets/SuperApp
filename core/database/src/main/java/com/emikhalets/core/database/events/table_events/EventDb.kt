package com.emikhalets.core.database.events.table_events

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "group_id") val groupId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "with_year") val withYear: Boolean,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "note") val note: String,
)