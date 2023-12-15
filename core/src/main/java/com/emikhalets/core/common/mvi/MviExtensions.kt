package com.emikhalets.core.common.mvi

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job = viewModelScope.launch { block() }

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchMain(
    block: suspend CoroutineScope.() -> Unit,
): Job = viewModelScope.launch(Dispatchers.Main) { block() }

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchDefault(
    block: suspend CoroutineScope.() -> Unit,
): Job = viewModelScope.launch(Dispatchers.Default) { block() }

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchIO(
    block: suspend CoroutineScope.() -> Unit,
): Job = viewModelScope.launch(Dispatchers.IO) { block() }
