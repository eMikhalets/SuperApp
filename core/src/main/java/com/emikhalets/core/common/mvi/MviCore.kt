package com.emikhalets.core.common.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface UiAction
interface UiEffect
interface UiState

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch { block() }

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchInMain(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(Dispatchers.Main) { block() }

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchInDefault(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(Dispatchers.Default) { block() }

fun <A : UiAction, E : UiEffect, S : UiState> MviViewModel<A, E, S>.launchInIO(
    block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(Dispatchers.IO) { block() }
