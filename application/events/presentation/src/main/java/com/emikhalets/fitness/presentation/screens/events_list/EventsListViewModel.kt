package com.emikhalets.fitness.presentation.screens.events_list

import com.emikhalets.events.domain.entity.EventEntity
import com.emikhalets.events.domain.usecase.events.EventsGetUseCase
import com.emikhalets.fitness.presentation.screens.events_list.EventsListContract.Action
import com.emikhalets.fitness.presentation.screens.events_list.EventsListContract.Effect
import com.emikhalets.fitness.presentation.screens.events_list.EventsListContract.State
import com.emikhalets.ui.BaseViewModel
import com.emikhalets.ui.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EventsListViewModel @Inject constructor(
    private val eventsGetUseCase: EventsGetUseCase,
) : BaseViewModel<State, Effect, Action>() {

    private var searchJob: Job? = null

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.GetEvents -> getEvents()
            is Action.SearchEvents -> searchEvents(action.query)
        }
    }

    private fun getEvents() {
        launchIOScope {
            setState { it.copy(isLoading = true) }
            eventsGetUseCase.invoke()
                .onSuccess { flow -> handleSuccess(flow) }
                .onFailure { code, message -> handleError(code, message) }
        }
    }

    private fun searchEvents(query: String) {
        cancelJob(searchJob)
        searchJob = launchIOScope {
            val list = eventsGetUseCase.invoke(query, currentState.events)
            setState { it.copy(events = list, query = query) }
        }
    }

    private suspend fun handleSuccess(flow: Flow<List<EventEntity>>) {
        flow.collectLatest { list ->
            val events = prepareEventsList(list)
            setState {
                it.copy(
                    isLoading = false,
                    savedEvents = events,
                    events = events
                )
            }
        }
    }

    private fun handleError(code: Int, message: String) {
        setState { it.copy(isLoading = false) }
        setEffect { Effect.ErrorDialog(UiString.create(message)) }
    }

    private suspend fun prepareEventsList(list: List<EventEntity>): List<EventEntity> {
        return withContext(Dispatchers.IO) {
            list.sortedBy { event -> event.days }
        }
    }
}
