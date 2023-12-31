package com.emikhalets.notes.data.database

import android.content.Context
import com.emikhalets.notes.data.database.table_notes.NotesDao
import com.emikhalets.notes.data.database.table_subtasks.SubtasksDao
import com.emikhalets.notes.data.database.table_tasks.TasksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotesDatabaseModule {

    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase {
        return NotesDatabase.getInstance(context)
    }

    @Provides
    fun providesNotesDao(database: NotesDatabase): NotesDao {
        return database.notesDao
    }

    @Provides
    fun providesTasksDao(database: NotesDatabase): TasksDao {
        return database.tasksDao
    }

    @Provides
    fun providesSubtasksDao(database: NotesDatabase): SubtasksDao {
        return database.subtasksDao
    }
}
