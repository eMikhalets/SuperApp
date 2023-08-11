package com.emikhalets.events.data.database.table.events

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "group_id") val groupId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "note") val note: String = "",
    @ColumnInfo(name = "is_with_year") val isWithYear: Boolean,
    @ColumnInfo(name = "is_hide") val isHide: Boolean,
    @ColumnInfo(name = "is_alarm") val isAlarm: Boolean,
)
