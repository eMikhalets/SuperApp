package com.emikhalets.superapp.domain.notes.model

data class TaskModel(
    val id: Long,
    val content: String,
    val completed: Boolean,
    val createDate: Long,
    val updateDate: Long,
    val subtasks: List<SubtaskModel>,
)
