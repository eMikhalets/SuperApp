package com.emikhalets.core.database.media.table_serials

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.emikhalets.core.database.media.type_converter.CrewConverters
import com.emikhalets.core.database.media.type_converter.GenresConverters
import com.emikhalets.core.database.media.type_converter.RatingsConverters
import java.util.*

@Entity(tableName = "serials")
data class SerialDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "title_ru") val titleRu: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster") val poster: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "save_timestamp") val saveTimestamp: Long,
    @ColumnInfo(name = "last_update_timestamp") val lastUpdateTimestamp: Long,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "watch_status") val watchStatus: String,
//    @TypeConverters(GenresConverters::class)
    @ColumnInfo(name = "genres") val genres: String,
//    @TypeConverters(RatingsConverters::class)
    @ColumnInfo(name = "ratings", defaultValue = "") val ratings: String,
    @ColumnInfo(name = "runtime", defaultValue = "") val runtime: String,
//    @TypeConverters(CrewConverters::class)
    @ColumnInfo(name = "crew", defaultValue = "") val crew: String,
    @ColumnInfo(name = "awards", defaultValue = "") val awards: String,
)