package com.emikhalets.core.database.media

import android.content.Context
import com.emikhalets.core.database.media.table_crew.CrewDao
import com.emikhalets.core.database.media.table_genres.GenresDao
import com.emikhalets.core.database.media.table_movies.MoviesDao
import com.emikhalets.core.database.media.table_serials.SerialsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MediaDatabaseModule {

    @Provides
    fun providesMediaDatabase(@ApplicationContext context: Context): MediaDatabase {
        return MediaDatabase.getInstance(context)
    }

    @Provides
    fun providesCrewDao(database: MediaDatabase): CrewDao {
        return database.crewDao
    }

    @Provides
    fun providesGenresDao(database: MediaDatabase): GenresDao {
        return database.genresDao
    }

    @Provides
    fun providesMoviesDao(database: MediaDatabase): MoviesDao {
        return database.moviesDao
    }

    @Provides
    fun providesSerialsDao(database: MediaDatabase): SerialsDao {
        return database.serialsDao
    }
}
