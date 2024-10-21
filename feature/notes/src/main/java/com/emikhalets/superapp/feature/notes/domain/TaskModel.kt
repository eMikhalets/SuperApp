package com.emikhalets.superapp.feature.notes.domain

import androidx.compose.runtime.Immutable
import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.common.timestamp

@Immutable
data class TaskModel(
    val id: Long,
    val header: String,
    val createDate: Long,
    val subtasks: List<SubTaskModel>,
) {

    constructor() : this(
        id = Const.IdNew,
        header = "",
        createDate = timestamp(),
        subtasks = emptyList()
    )
}

@Immutable
data class SubTaskModel(
    val id: Long,
    val parentId: Long,
    val text: String,
    val completed: Boolean,
    val createDate: Long,
) {

    constructor() : this(
        id = Const.IdNew,
        parentId = Const.IdNew,
        text = "",
        completed = false,
        createDate = timestamp(),
    )
}
