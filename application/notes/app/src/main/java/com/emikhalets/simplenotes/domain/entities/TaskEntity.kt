package com.emikhalets.simplenotes.domain.entities

import java.util.*

data class TaskEntity(
    val id: Long = 0,
    val content: String,
    val checked: Boolean = false,
    val savedTime: Long = Date().time,
    val subtasks: List<SubtaskEntity> = emptyList(),
) {

    companion object {

        fun empty() = TaskEntity(
            id = 0,
            content = "",
            checked = false,
            savedTime = Date().time,
        )
    }
}
