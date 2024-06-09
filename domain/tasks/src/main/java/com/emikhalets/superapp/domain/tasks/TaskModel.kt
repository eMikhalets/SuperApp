package com.emikhalets.superapp.domain.tasks

import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.common.date.DateHelper

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
        createDate = DateHelper.now,
        updateDate = DateHelper.now,
        subtasks = emptyList()
    )
}
