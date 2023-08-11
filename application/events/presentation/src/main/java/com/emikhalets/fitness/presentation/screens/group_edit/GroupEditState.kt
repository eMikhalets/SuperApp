package com.emikhalets.simpleevents.presentation.screens.group_edit

import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.ui.UiState

data class GroupEditState(
    val group: GroupEntity? = null,
    val loading: Boolean = false,
    val saved: Boolean = false,
    val error: UiString? = null,
) : UiState
