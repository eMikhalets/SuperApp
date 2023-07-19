package com.emikhalets.core.database.media.table_crew

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crew")
data class CrewDb(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
)