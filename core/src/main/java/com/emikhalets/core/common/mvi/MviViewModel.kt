package com.emikhalets.core.common.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MviViewModel<A : UiAction, E : UiEffect, S : UiState> : ViewModel() {

    private val initialState: S by lazy { setInitialState() }

    private val _action: MutableSharedFlow<A> = MutableSharedFlow()
    val action get() = _action.asSharedFlow()

    private val _effect: Channel<E> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state get() = _state.asStateFlow()

    private val errorHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception -> handleError(exception.message) }

    val currentState: S get() = state.value
    val scope: CoroutineScope = CoroutineScope(SupervisorJob() + errorHandler)

    init {
        subscribeActions()
    }

    protected abstract fun setInitialState(): S
    protected abstract fun handleAction(action: A)
    protected abstract fun handleError(message: String?)

    fun setAction(action: A) = scope.launch { _action.emit(action) }
    protected fun setEffect(builder: () -> E) = scope.launch { _effect.send(builder()) }
    protected fun setState(reduce: (S) -> S) = _state.update { reduce(it) }
    private fun subscribeActions() = scope.launch { action.collect { handleAction(it) } }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}
