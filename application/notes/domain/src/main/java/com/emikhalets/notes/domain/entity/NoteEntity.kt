package com.emikhalets.notes.domain.entity

import java.util.Date

data class NoteEntity(
    val id: Long,
    val title: String,
    val content: String,
    val createTimestamp: Long,
    val updateTimestamp: Long,
) {

    constructor(title: String, content: String) : this(
        id = 0,
        title = title,
        content = content,
        createTimestamp = Date().time,
        updateTimestamp = Date().time
    )
}
