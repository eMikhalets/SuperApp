package com.emikhalets.core.common.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface UiAction
interface UiEffect
interface UiState

fun ViewModel.launchScope(block: suspend CoroutineScope.() -> Unit): Job =
    viewModelScope.launch { block() }

fun ViewModel.launchMainScope(block: suspend CoroutineScope.() -> Unit): Job =
    viewModelScope.launch(Dispatchers.Main) { block() }

fun ViewModel.launchDefaultScope(block: suspend CoroutineScope.() -> Unit): Job =
    viewModelScope.launch(Dispatchers.Default) { block() }

fun ViewModel.launchIOScope(block: suspend CoroutineScope.() -> Unit): Job =
    viewModelScope.launch(Dispatchers.IO) { block() }
