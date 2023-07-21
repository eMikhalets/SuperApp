package com.emikhalets.core.database.media

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emikhalets.core.common.logi
import com.emikhalets.core.database.media.table_crew.CrewDao
import com.emikhalets.core.database.media.table_crew.CrewDb
import com.emikhalets.core.database.media.table_genres.GenreDb
import com.emikhalets.core.database.media.table_genres.GenresDao
import com.emikhalets.core.database.media.table_movies.MovieDb
import com.emikhalets.core.database.media.table_movies.MoviesDao
import com.emikhalets.core.database.media.table_serials.SerialDb
import com.emikhalets.core.database.media.table_serials.SerialsDao
import com.emikhalets.core.database.media.type_converter.CrewConverters
import com.emikhalets.core.database.media.type_converter.GenresConverters
import com.emikhalets.core.database.media.type_converter.RatingsConverters

@Database(
    entities = [
        CrewDb::class,
        GenreDb::class,
        MovieDb::class,
        SerialDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
//@TypeConverters(GenresConverters::class, RatingsConverters::class, CrewConverters::class)
abstract class MediaDatabase : RoomDatabase() {

    abstract val crewDao: CrewDao
    abstract val genresDao: GenresDao
    abstract val moviesDao: MoviesDao
    abstract val serialsDao: SerialsDao

    companion object {

        private const val TAG = "MediaDatabase"

        @Volatile
        private var instance: MediaDatabase? = null

        fun getInstance(context: Context): MediaDatabase {
            logi(TAG, "Get instance")
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): MediaDatabase {
            logi(TAG, "Building database")
            return Room
                .databaseBuilder(context, MediaDatabase::class.java, "Media.db")
                .build()
        }
    }
}