package com.emikhalets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState
interface UiEffect
interface UiAction

abstract class BaseViewModel<S : UiState, E : UiEffect, A : UiAction> : ViewModel() {

    private val initialState: S by lazy { createInitialState() }

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state get() = _state.asStateFlow()

    private val _action: MutableSharedFlow<A> = MutableSharedFlow()
    val action get() = _action.asSharedFlow()

    private val _effect: Channel<E> = Channel()
    val effect get() = _effect.receiveAsFlow()

    val currentState: S get() = state.value

    init {
        subscribeEvents()
    }

    abstract fun createInitialState(): S

    protected abstract fun handleEvent(action: A)

    fun setAction(action: A) = viewModelScope.launch { _action.emit(action) }

    protected fun setState(reduce: (S) -> S) = _state.update { reduce(it) }

    protected fun setEffect(builder: () -> E) = viewModelScope.launch { _effect.send(builder()) }

    private fun subscribeEvents() = viewModelScope.launch { action.collect { handleEvent(it) } }

    fun launchMainScope(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.Main) { block() }

    fun launchDefaultScope(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.Default) { block() }

    fun launchIOScope(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.IO) { block() }
}
