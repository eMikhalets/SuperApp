package com.emikhalets.superapp.domain.notes.model

data class SubtaskModel(
    val id: Long,
    val parentId: Long,
    val content: String,
    val completed: Boolean,
    val createDate: Long,
    val updateDate: Long,
)
