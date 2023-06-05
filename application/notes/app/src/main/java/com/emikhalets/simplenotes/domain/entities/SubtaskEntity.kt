package com.emikhalets.simplenotes.domain.entities

import java.util.*

data class SubtaskEntity(
    val id: Long = 0,
    val taskId: Long,
    val content: String,
    val checked: Boolean = false,
    val savedTime: Long = Date().time,
) {

    companion object {

        fun empty(taskId: Long) = SubtaskEntity(
            id = 0,
            taskId = taskId,
            content = "",
            checked = false,
            savedTime = Date().time,
        )
    }
}
