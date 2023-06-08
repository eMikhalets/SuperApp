package com.emikhalets.fitness.presentation.screens.event_item

import com.emikhalets.common.AppError
import com.emikhalets.common.onFailure
import com.emikhalets.common.onSuccess
import com.emikhalets.events.domain.usecase.events.EventsDeleteUseCase
import com.emikhalets.events.domain.usecase.events.EventsGetUseCase
import com.emikhalets.fitness.presentation.screens.event_item.EventItemContract.Action
import com.emikhalets.fitness.presentation.screens.event_item.EventItemContract.Effect
import com.emikhalets.fitness.presentation.screens.event_item.EventItemContract.State
import com.emikhalets.simpleevents.utils.extensions.calculateEventData
import com.emikhalets.ui.BaseViewModel
import com.emikhalets.ui.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventItemViewModel @Inject constructor(
    private val eventsGetUseCase: EventsGetUseCase,
    private val eventsDeleteUseCase: EventsDeleteUseCase,
) : BaseViewModel<State, Effect, Action>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            is Action.GetEvent -> getEvent(action.id)
            Action.DeleteEvent -> deleteEvent()
        }
    }

    private fun getEvent(id: Long?) {
        if (id != null) {
            launchIOScope {
                setState { it.copy(isLoading = true) }
                eventsGetUseCase.invoke(id)
                    .onSuccess { result ->
                        val event = result.calculateEventData()
                        setState { it.copy(loading = false, event = event) }
                    }
                    .onFailure { code, message -> handleError(code, message) }
            }
        } else {
            handleError(AppError.Internal)
        }
    }

    fun deleteEvent() {
        val entity = currentState.event
        if (entity == null) {
            handleError(AppError.Internal)
            return
        }

        launchIOScope {
            eventsDeleteUseCase.invoke(entity)
                .onSuccess { handleDeleted() }
                .onFailure { code, message -> handleError(code, message) }
        }
    }

    private fun handleDeleted() {
        setState { it.copy(isDeleted = true) }
    }

    private fun handleError(code: Int, message: String) {
        setState { it.copy(isLoading = false) }
        setEffect { Effect.ErrorDialog(UiString.create(message)) }
    }

    private fun handleError(error: AppError) {
        setState { it.copy(isLoading = false) }
        setEffect { Effect.ErrorDialog(UiString.create(error)) }
    }
}
