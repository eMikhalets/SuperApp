package com.emikhalets.simpleevents.presentation.screens.edit_event

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.events.domain.usecase.events.AddEventUseCase
import com.emikhalets.events.domain.usecase.events.EventsGetUseCase
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListAction
import com.emikhalets.ui.BaseViewModel
import com.emikhalets.simpleevents.utils.extensions.calculateEventData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(
    private val getEventsUseCase: EventsGetUseCase,
    private val addEventUseCase: AddEventUseCase,
) : BaseViewModel<EditEventState, EventsListAction>() {

    override fun createInitialState(): EditEventState = EditEventState()

    override fun handleEvent(action: EventsListAction) {
    }

    fun resetError() = setState { it.copy(error = null) }
    fun resetUpdated() = setState { it.copy(updated = false) }

    fun loadEvent(id: Long) {
        launchIO {
            setState { it.copy(loading = true) }
            getEventsUseCase(id)
                .onSuccess { result ->
                    val event = result.calculateEventData()
                    setState { it.copy(loading = false, event = event) }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun updateEvent(event: EventEntity) {
        launchIO {
            setState { it.copy(loading = true) }
            addEventUseCase(event)
                .onSuccess {
                    setState { it.copy(loading = false, updated = true) }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
