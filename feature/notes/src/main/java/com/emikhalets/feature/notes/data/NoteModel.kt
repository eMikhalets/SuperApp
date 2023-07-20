package com.emikhalets.feature.notes.data

import java.util.Date

data class NoteModel(
    val id: Long,
    val title: String,
    val content: String,
    val createDate: Long,
    val updateDate: Long,
) {

    constructor() : this("", "")

    constructor(title: String, content: String) : this(0, title, content)

    constructor(id: Long, title: String, content: String)
            : this(id, title, content, Date().time, Date().time)
}
