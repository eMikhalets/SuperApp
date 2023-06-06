package com.emikhalets.events.data.database.table.alarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "enabled") val enabled: Boolean,
    @ColumnInfo(name = "milliseconds") val milliseconds: Long,
)
