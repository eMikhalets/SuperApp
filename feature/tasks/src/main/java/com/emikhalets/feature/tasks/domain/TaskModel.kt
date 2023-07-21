package com.emikhalets.feature.tasks.domain

import java.util.Date

data class TaskModel(
    val id: Long,
    val content: String,
    val completed: Boolean,
    val createDate: Long,
    val updateDate: Long,
    val subtasks: List<TaskModel>,
) {

    val subtasksCount: Int = subtasks.count()
    val subtasksCompletedCount: Int = subtasks.count { it.completed }

    constructor() : this("")

    constructor(content: String)
            : this(content, false)

    constructor(content: String, completed: Boolean)
            : this(content, completed, emptyList())

    constructor(content: String, subtasks: List<TaskModel>)
            : this(content, false, subtasks)

    constructor(content: String, completed: Boolean, subtasks: List<TaskModel>)
            : this(0, content, completed, subtasks)

    constructor(id: Long, content: String)
            : this(id, content, false, emptyList())

    constructor(id: Long, content: String, completed: Boolean)
            : this(id, content, completed, emptyList())

    constructor(id: Long, content: String, subtasks: List<TaskModel>)
            : this(id, content, false, subtasks)

    constructor(id: Long, content: String, completed: Boolean, subtasks: List<TaskModel>)
            : this(id, content, completed, Date().time, Date().time, subtasks)
}
