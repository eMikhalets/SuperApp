package com.emikhalets.superapp.core.common.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

abstract class MviViewModel<A : MviAction, E : MviEffect, S : MviState> : ViewModel() {

    private val initialState: S by lazy { setInitialState() }

    private val _action: MutableSharedFlow<A> = MutableSharedFlow()
    val action get() = _action.asSharedFlow()

    private val _effect: Channel<E> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state get() = _state.asStateFlow()

    val currentState: S get() = state.value

    init {
        subscribeActions()
    }

    protected abstract fun setInitialState(): S
    protected abstract fun handleAction(action: A)

    fun setAction(action: A) = launch { _action.emit(action) }
    protected fun setEffect(builder: () -> E) = launch { _effect.send(builder()) }
    protected fun setState(reduce: (S) -> S) = _state.update { reduce(it) }
    private fun subscribeActions() = launch { action.collect { handleAction(it) } }
}
