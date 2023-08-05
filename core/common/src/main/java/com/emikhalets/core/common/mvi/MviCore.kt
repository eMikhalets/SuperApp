package com.emikhalets.core.common.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface UiAction
interface UiEffect
interface UiState

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchScope(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch { block() }

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchMainScope(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(Dispatchers.Main) { block() }

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchDefaultScope(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(Dispatchers.Default) { block() }

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchIOScope(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(Dispatchers.IO) { block() }
