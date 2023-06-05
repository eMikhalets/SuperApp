package com.emikhalets.simpleevents.presentation.screens.groups

import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.events.domain.use_case.groups.AddGroupUseCase
import com.emikhalets.events.domain.use_case.groups.GetGroupsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val addGroupUseCase: AddGroupUseCase,
) : BaseViewModel<GroupsState, GroupsAction>() {

    override fun createInitialState() = GroupsState()

    override fun handleEvent(action: GroupsAction) {
        when (action) {
            GroupsAction.GetGroups -> getGroups()
            GroupsAction.AcceptError -> resetError()
            is GroupsAction.UpdateGroup -> updateGroupAlarm(action.entity, action.enabled)
        }
    }

    private fun resetError() = setState { it.copy(error = null) }

    private fun getGroups() {
        launchIO {
            setState { it.copy(loading = true) }
            getGroupsUseCase()
                .onSuccess { result ->
                    result.collectLatest { list ->
                        setState { it.copy(loading = false, groupsList = list) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    private fun updateGroupAlarm(entity: GroupEntity, enabled: Boolean) {
        launchIO {
            addGroupUseCase(entity.copy(isAlarmsEnabled = enabled))
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
