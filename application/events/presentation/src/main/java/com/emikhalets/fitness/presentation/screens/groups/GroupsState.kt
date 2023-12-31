package com.emikhalets.simpleevents.presentation.screens.groups

import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.utils.AppState

data class GroupsState(
    val groupsList: List<GroupEntity> = emptyList(),
    val loading: Boolean = false,
    val error: UiString? = null,
) : AppState
