package com.emikhalets.superapp.feature.notes.domain

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.timestamp

@Immutable
data class TaskModel(
    val id: Long = 0,
    val header: String = "",
    val createDate: Long = timestamp(),
    val subtasks: List<SubTaskModel> = emptyList(),
)

@Immutable
data class SubTaskModel(
    val id: Long = 0,
    val parentId: Long = 0,
    val text: String = "",
    val completed: Boolean = false,
    val createDate: Long = timestamp(),
)
