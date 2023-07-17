package com.emikhalets.events.data.database.table.events

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.events.domain.entity.EventType

@Entity(tableName = "events")
data class EventDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "eventType") val eventType: EventType,
    @ColumnInfo(name = "note") val note: String = "",
    @ColumnInfo(name = "without_year", defaultValue = "false") val withoutYear: Boolean = false,
    @ColumnInfo(name = "group_id", defaultValue = "false") val groupId: Long = 0L,
)
