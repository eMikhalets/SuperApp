package com.emikhalets.core.database.notes.table_notes

import com.emikhalets.core.common.logi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NotesLocalDataSource @Inject constructor(
    private val dao: NotesDao,
) {

    fun getNotes(): Flow<List<NoteDb>> {
        logi(TAG, "Get all flow order by update date")
        return dao.getAllFlowOrderUpdateDateDesc()
    }

    suspend fun insertNote(model: NoteDb) {
        logi(TAG, "Insert item: $model")
        return dao.insert(model)
    }

    suspend fun insertNotes(list: List<NoteDb>) {
        logi(TAG, "Insert list: ${list.count()}")
        return dao.insert(list)
    }

    suspend fun updateNote(model: NoteDb) {
        logi(TAG, "Update item: $model")
        return dao.update(model)
    }

    suspend fun deleteNote(model: NoteDb) {
        logi(TAG, "Delete item: $model")
        return dao.delete(model)
    }

    companion object {

        private const val TAG = "NotesLocalDS"
    }
}