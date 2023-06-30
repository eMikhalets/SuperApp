package com.emikhalets.notes.app

import com.emikhalets.core.common.AppResult
import com.emikhalets.notes.domain.entity.NoteEntity
import com.emikhalets.notes.domain.usecase.NotesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NotesUseCaseTest : NotesUseCase {

    override suspend fun insert(entity: NoteEntity): AppResult<Unit> {
        return AppResult.success(Unit)
    }

    override suspend fun update(entity: NoteEntity): AppResult<Unit> {
        return AppResult.success(Unit)
    }

    override suspend fun delete(entity: NoteEntity): AppResult<Unit> {
        return AppResult.success(Unit)
    }

    override fun getAllFlow(): AppResult<Flow<List<NoteEntity>>> {
        return AppResult.success(
            flowOf(
                listOf(
                    NoteEntity("Note title 1", "Note content 1"),
                    NoteEntity("Note title 2", "Note content 2"),
                    NoteEntity("Note title 3", "Note content 3"),
                    NoteEntity("Note title 4", "Note content 4"),
                    NoteEntity("Note title 5", "Note content 5"),
                )
            )
        )
    }

    override suspend fun getItem(id: Long): AppResult<NoteEntity> {
        return AppResult.success(NoteEntity("Note title 1", "Note content 1"))
    }
}