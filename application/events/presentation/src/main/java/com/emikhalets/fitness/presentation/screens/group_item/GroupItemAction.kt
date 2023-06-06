package com.emikhalets.simpleevents.presentation.screens.group_item

import com.emikhalets.ui.UiAction

sealed class GroupItemAction : UiAction {
    object AcceptError : GroupItemAction()
    object GetEvents : GroupItemAction()
    class GetGroup(val id: Long?) : GroupItemAction()
}
