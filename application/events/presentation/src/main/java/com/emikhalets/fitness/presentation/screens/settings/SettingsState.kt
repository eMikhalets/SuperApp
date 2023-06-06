package com.emikhalets.simpleevents.presentation.screens.settings

import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.ui.UiState

data class SettingsState(
    val eventAlarms: List<AlarmEntity> = emptyList(),
    val imported: Boolean = false,
    val exported: Boolean = false,
    val loading: Boolean = false,
    val error: UiString? = null,
) : UiState
