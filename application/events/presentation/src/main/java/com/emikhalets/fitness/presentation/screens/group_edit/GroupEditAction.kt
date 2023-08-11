package com.emikhalets.simpleevents.presentation.screens.group_edit

import com.emikhalets.ui.UiAction

sealed class GroupEditAction : UiAction {
    object AcceptError : GroupEditAction()
    class GetGroup(val id: Long?) : GroupEditAction()
    class UpdateGroup(val name: String, val enabled: Boolean) : GroupEditAction()
}
