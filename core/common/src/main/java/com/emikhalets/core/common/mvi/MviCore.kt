package com.emikhalets.core.common.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface UiAction
interface UiState

fun <A : UiAction, S : UiState> BaseViewModel<A, S>.launchScope(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch { block() }

fun <A : UiAction, S : UiState> BaseViewModel<A, S>.launchMainScope(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(Dispatchers.Main) { block() }

fun <A : UiAction, S : UiState> BaseViewModel<A, S>.launchDefaultScope(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(Dispatchers.Default) { block() }

fun <A : UiAction, S : UiState> BaseViewModel<A, S>.launchIOScope(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(Dispatchers.IO) { block() }
