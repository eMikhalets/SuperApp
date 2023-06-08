package com.emikhalets.notes.presentation.screens.main

import com.emikhalets.common.UiString

data class MenuState(
    val workoutInitialized: Boolean = false,
    val error: UiString? = null,
)
