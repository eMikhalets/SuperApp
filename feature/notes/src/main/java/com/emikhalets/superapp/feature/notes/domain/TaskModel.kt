package com.emikhalets.superapp.feature.notes.domain

import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.common.timestamp

data class TaskModel(
    val id: Long,
    val parentId: Long,
    val content: String,
    val completed: Boolean,
    val createDate: Long,
    val updateDate: Long,
    val subtasks: List<TaskModel>,
) {

    constructor() : this(
        id = Const.IdNew,
        parentId = Const.IdNew,
        content = "",
        completed = false,
        createDate = timestamp(),
        updateDate = timestamp(),
        subtasks = emptyList()
    )
}
