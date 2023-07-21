package com.emikhalets.feature.notes.data

import com.emikhalets.core.common.logi
import com.emikhalets.core.database.notes.LocalNotesDataSource
import com.emikhalets.feature.notes.domain.NoteModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository @Inject constructor(
    private val localDataSource: LocalNotesDataSource,
) {

    suspend fun insertNote(model: NoteModel) {
        logi(TAG, "Insert note: $model")
        localDataSource.insertNote(model.toDb())
    }

    suspend fun updateNote(model: NoteModel) {
        logi(TAG, "Update note: $model")
        localDataSource.updateNote(model.toDb())
    }

    suspend fun deleteNote(model: NoteModel) {
        logi(TAG, "Delete note: $model")
        localDataSource.deleteNote(model.toDb())
    }

    fun getNotes(): Flow<List<NoteModel>> {
        logi(TAG, "Get all notes")
        return localDataSource.getNotes().map { it.toModelList() }
    }

    companion object {

        private const val TAG = "FeatureNotesRepo"
    }
}