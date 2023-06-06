package com.emikhalets.fitness.presentation.screens.main

import com.emikhalets.common.onFailure
import com.emikhalets.common.onSuccess
import com.emikhalets.events.domain.usecase.alarms.AddDefaultAlarmsUseCase
import com.emikhalets.fitness.presentation.screens.main.MainContract.Action
import com.emikhalets.fitness.presentation.screens.main.MainContract.Effect
import com.emikhalets.fitness.presentation.screens.main.MainContract.State
import com.emikhalets.ui.BaseViewModel
import com.emikhalets.ui.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addDefaultAlarmsUseCase: AddDefaultAlarmsUseCase,
) : BaseViewModel<State, Effect, Action>() {

    override fun createInitialState() = State()

    override fun handleEvent(action: Action) {
        when (action) {
            Action.CreateDefaultAlarms -> createDefaultAlarms()
        }
    }

    private fun createDefaultAlarms() {
        launchIOScope {
            setState { it.copy(isLoading = true) }
            addDefaultAlarmsUseCase.invoke()
                .onSuccess { handleSuccess() }
                .onFailure { code, message -> handleError(code, message) }
        }
    }

    private fun handleSuccess() {
        setState { it.copy(isLoading = false, isAlarmsCreated = true) }
    }

    private fun handleError(code: Int, message: String) {
        setState { it.copy(isLoading = false) }
        setEffect { Effect.ErrorDialog(UiString.create(message)) }
    }
}
