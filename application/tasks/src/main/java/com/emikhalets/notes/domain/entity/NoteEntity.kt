package com.emikhalets.notes.domain.entity

data class NoteEntity(
    val id: Long,
    val title: String,
    val content: String,
    val createTimestamp: Long,
    val updateTimestamp: Long,
)
