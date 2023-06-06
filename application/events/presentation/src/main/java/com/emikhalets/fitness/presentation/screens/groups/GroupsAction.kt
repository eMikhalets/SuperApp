package com.emikhalets.simpleevents.presentation.screens.groups

import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.ui.UiAction

sealed class GroupsAction : UiAction {
    object AcceptError : GroupsAction()
    object GetGroups : GroupsAction()
    class UpdateGroup(val entity: GroupEntity, val enabled: Boolean) : GroupsAction()
}
